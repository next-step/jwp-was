package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.LoginController;
import controller.UserCreateController;
import db.DataBase;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.Cookie;
import model.HttpHeaders;
import model.User;
import model.request.HttpRequest;
import model.request.RequestBody;
import model.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String PATH_DELIMITER = "/";
    private static final String CSS_PATH = "/css";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            RequestLine requestLine = (new RequestLine(br.readLine())).parse();
            HttpHeaders httpHeaders = createHttpHeaders(br);
            RequestBody requestBody = new RequestBody(br, httpHeaders);
            Cookie cookie = Cookie.from(httpHeaders.get(Cookie.REQUEST_COOKIE_HEADER));
            HttpRequest httpRequest = new HttpRequest(httpHeaders, requestLine, requestBody, cookie);
            HttpResponse httpResponse = new HttpResponse();
            DataOutputStream dos = new DataOutputStream(out);
            String path = httpRequest.getHttpPath();
            Map<String, String> parameters = httpRequest.getRequestParameters().getRequestParameters();
            if (path.endsWith(".html")) {
                path = "./templates" + path;
                byte[] body = FileIoUtils.loadFileFromClasspath(path);
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else if ("/user/create".equals(path)) {
                UserCreateController userCreateController = new UserCreateController();
                userCreateController.doPost(httpRequest, httpResponse);
            } else if ("/user/login".equals(path)) {
                LoginController loginController = new LoginController();
                loginController.doPost(httpRequest, httpResponse);
            } else if ("/user/list".equals(path)) {
                if (!Boolean.parseBoolean(cookie.get("logined"))) {
                    response302Header(dos, "user/login.html");
                    return;
                }

                List<User> users = new ArrayList<>(DataBase.findAll());
                TemplateLoader templateLoader = new ClassPathTemplateLoader();
                templateLoader.setPrefix("/templates");
                templateLoader.setSuffix(".html");
                Handlebars handlebars = new Handlebars(templateLoader);
                Template template = handlebars.compile("user/list");
                byte[] listPage = template.apply(users).getBytes();

                response200Header(dos, listPage.length);
                responseBody(dos, listPage);
            } else if (isStaticPath(path)) {
                String prefix = "static";
                if (path.contains(".html") || path.contains(".ico")) {
                    prefix = "templates";
                }

                byte[] responseBody = FileIoUtils.loadFileFromClasspath("./" + prefix + "/" + path);

                response200Header(dos, responseBody.length);
                responseBody(dos, responseBody);
            }

            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private HttpHeaders createHttpHeaders(BufferedReader br) throws IOException {
        List<String> headerLine = new ArrayList<>();
        String line = br.readLine();
        while (!line.equals("")) {
            headerLine.add(line);
            line = br.readLine();
        }

        return new HttpHeaders(String.join("\n", headerLine));
    }

    private boolean isStaticPath(String path) {
        return path.startsWith(CSS_PATH);
    }

    private boolean isLogin(User loginUser, Map<String, String> parameters) {
        return !Objects.isNull(loginUser) && loginUser.isPassword(parameters.get("password"));
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            responseHeader(dos, "Location", PATH_DELIMITER + location);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, String key, String value) {
        try {
            dos.writeBytes(key + ": " + value + "\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
