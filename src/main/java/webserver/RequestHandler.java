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
import webserver.http.request.*;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Handlebars handlebars;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String requestLineString = br.readLine();
            List<String> headerLineStrings = new ArrayList<>();
            String nextLine = br.readLine();
            while (!Objects.isNull(nextLine) && !"".equals(nextLine)) {
                headerLineStrings.add(nextLine);
                nextLine = br.readLine();
            }
            String headerLineString = String.join("\n", headerLineStrings);

            RequestLine requestLine = RequestLine.from(requestLineString);
            HttpMethod method = requestLine.getMethod();
            Path path = requestLine.getPath();
            HttpHeaders httpHeaders = HttpHeaders.from(headerLineString);
            Cookie cookie = Cookie.from(httpHeaders.get(Cookie.REQUEST_COOKIE_HEADER));

            String requestBody = "";
            if (HttpMethod.POST.equals(method)) {
                int contentLength = Integer.parseInt(httpHeaders.get("Content-Length"));
                requestBody = IOUtils.readData(br, contentLength);
            }

            Parameters parameters = HttpMethod.GET.equals(method) ? path.getQueries() : Parameters.from(requestBody);

            DataOutputStream dos = new DataOutputStream(out);
            String pathString = path.toString();
            switch (pathString) {
                case "user/create":
                    User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
                    DataBase.addUser(user);

                    response302Header(dos, "index.html");
                    break;
                case "user/login":
                    User loginUser = DataBase.findUserById(parameters.get("userId"));
                    boolean isLogin = !Objects.isNull(loginUser) && loginUser.checkPassword(parameters.get("password"));
                    cookie.set("logined", String.valueOf(isLogin));

                    response302Header(dos, isLogin ? "index.html" : "user/login_failed.html");
                    responseHeader(dos, Cookie.RESPONSE_COOKIE_HEADER, cookie.getResponseCookie());
                    break;
                case "user/list":
                    boolean logined = Boolean.parseBoolean(cookie.get("logined"));
                    if (!logined) {
                        response302Header(dos, "user/login.html");
                        break;
                    }

                    List<User> users = new ArrayList<>(DataBase.findAll());
                    Template template = handlebars.compile("user/list");
                    byte[] listPage = template.apply(users).getBytes();

                    response200Header(dos, listPage.length);
                    responseBody(dos, listPage);
                    break;
                default:
                    pathString = !"".equals(pathString) ? pathString : "index.html";
                    String prefix = (pathString.contains(".html") || pathString.contains(".ico")) ? "templates" : "static";
                    byte[] responseBody = FileIoUtils.loadFileFromClasspath("./" + prefix + "/" + pathString);

                    response200Header(dos, responseBody.length);
                    responseBody(dos, responseBody);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            responseHeader(dos, "Content-Type", "text/html;charset=utf-8");
            responseHeader(dos, "Content-Length", String.valueOf(lengthOfBodyContent));
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

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            responseHeader(dos, "Location", Path.PATH_DELIMITER + location);
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
}
