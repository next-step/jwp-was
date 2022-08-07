package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.ParamsUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String firstLine = br.readLine();
            logger.debug("request line : {}", firstLine);
            RequestLine requestLine = RequestLine.from(firstLine);
            logger.debug("RequestLine : {}", requestLine);

            HttpHeaders httpHeaders = new HttpHeaders();
            String line;
            while (!(line = br.readLine()).equals("")) {
                logger.debug("header : {}", line);
                httpHeaders.add(line);
            }
            logger.debug("HttpHeaders : {}", httpHeaders);

            DataOutputStream dos = new DataOutputStream(out);
            String path = requestLine.path();
            logger.debug("path : {}", path);

            Map<String, String> requestParams = new HashMap<>();
            HttpMethod method = requestLine.getMethod();
            logger.debug("method : {}", method);

            if (method.equals(HttpMethod.POST)) {
                String requestBody = IOUtils.readData(br, httpHeaders.getInt("Content-Length"));
                logger.debug("requestBody : {}", requestBody);
                requestParams = ParamsUtils.parsedQueryString(requestBody);
            }

            if (path.endsWith(".html")) {
                processPageRequest(dos, path);
            } else if (containsStaticPath(path)) {
                processStaticRequest(dos, path);
            } else if (path.startsWith("/user/create")) {
                join(requestParams, dos);
            } else if (path.startsWith("/user/login")) {
                login(requestParams, dos);
            } else if (path.startsWith("/user/list")) {
                list(httpHeaders, dos);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void processPageRequest(DataOutputStream dos, String path) throws IOException, URISyntaxException {
        path = "./templates" + path;
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private boolean containsStaticPath(String path) {
        return path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/font") || path.startsWith("/images");
    }

    private void processStaticRequest(DataOutputStream dos, String path) throws IOException, URISyntaxException {
        path = "./static" + path;
        byte[] body = FileIoUtils.loadFileFromClasspath(path);
        if (path.startsWith("./static/css")) {
            response200CssHeader(dos, body.length);
        } else {
            response200Header(dos, body.length);
        }
        responseBody(dos, body);
    }

    private void login(Map<String, String> requestParams, DataOutputStream dos) throws IOException, URISyntaxException {
        User loginUser = DataBase.findUserById(requestParams.get("userId"));
        logger.debug("로그인 사용자: {}", loginUser);

        boolean logined = false;
        String location = "/user/login_failed.html";

        if (loginUser != null && loginUser.getPassword().equals(requestParams.get("password"))) {
            logger.debug("로그인 성공: {}", loginUser);
            logined = true;
            location = "/index.html";
        }
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Set-Cookie: logined=" + logined + "; Path=/\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void join(Map<String, String> params, DataOutputStream dos) {
        User newUser = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        logger.debug("회원가입 사용자 : {}", newUser);
        DataBase.addUser(newUser);

        response302Header(dos);
    }

    private void list(HttpHeaders httpHeaders, DataOutputStream dos) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");

        if (httpHeaders.get("Cookie").equals("logined=true")) {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile("user/profile");

            User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
            String profilePage = template.apply(user);
            logger.debug("ProfilePage : {}", profilePage);

            body = profilePage.getBytes();
        }
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found\r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
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

    private void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
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
