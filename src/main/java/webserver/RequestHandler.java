package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);
            handle(request, response, new DataOutputStream(out));

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(HttpRequest request, HttpResponse response, DataOutputStream dos) throws IOException, URISyntaxException {
        RequestLine requestLine = request.getRequestLine();
        String path = requestLine.getPath();
        HttpMethod method = requestLine.getMethod();
        String requestBody = request.getRequestBody();
        HttpHeaders headers = request.getHeaders();
        String contentType = headers.getAccept();
        byte[] body;

        boolean isStaticResourcePath = Stream.of(".js", ".css", ".woff", ".ttf", ".ico")
            .anyMatch(path::endsWith);

        if (isStaticResourcePath) {
            body = FileIoUtils.loadFileFromClasspath("./static/" + path);
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
            return;
        }

        if (method.isGet() && ("/".equals(path) || "/index.html".equals(path))) {
            response.forward("index");
            return;
        }

        if (method.isGet() && "/user/form.html".equals(path)) {
            response.forward("user/form");
            return;
        }

        if (method.isGet() && "/user/login.html".equals(path)) {
            response.forward("user/login");
            return;
        }

        if (method.isGet() && "/user/list".equals(path)) {
            String cookie = headers.getCookie();
            if (cookie != null && cookie.contains("logined=true")) {
                Collection<User> users = DataBase.findAll();
                response.forward("user/list", users);
                return;
            }
            response.sendRedirect("/user/login.html");
            return;
        }

        if (method.isPost() && "/user/create".equals(path)) {
            createUser(requestBody);
            response.sendRedirect("/index.html");
            return;
        }

        if (method.isPost() && "/user/login".equals(path)) {
            if (isLoggedIn(requestBody)) {
                response.addHeader("Set-Cookie", "logined=true; Path=/");
                response.sendRedirect("/index.html");
                return;
            }
            response.addHeader("Set-Cookie", "logined=false");
            response.sendRedirect("/user/login_failed.html");
        }
    }

    private void createUser(String requestBody) {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");
        String name = parameters.getParameter("name");
        String email = parameters.getParameter("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }

    private boolean isLoggedIn(String requestBody) {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");

        User user = DataBase.findUserById(userId);
        return user != null && password.equals(user.getPassword());
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "; charset=utf-8\r\n");
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
