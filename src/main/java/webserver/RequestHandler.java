package webserver;

import static java.nio.charset.StandardCharsets.*;

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
        String cookie = headers.getCookie();
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

            body = showUsers(cookie);
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
            return;
        }

        if (method.isPost() && "/user/create".equals(path)) {
            body = createUser(requestBody);
            response302Header(dos);
            responseBody(dos, body);
            return;
        }

        if (method.isPost() && "/user/login".equals(path)) {
            body = login(requestBody, dos);
            responseBody(dos, body);
        }
    }

    private byte[] responseView(String viewName, Object model) {
        String view = HandlebarsUtils.getView(viewName, model);
        return view.getBytes(UTF_8);
    }

    private byte[] responseView(String viewName) {
        return responseView(viewName, null);
    }

    private byte[] showUsers(String cookie) {
        if (cookie != null && cookie.contains("logined=true")) {
            Collection<User> users = DataBase.findAll();
            return responseView("user/list", users);
        }
        return responseView( "user/login");
    }

    private byte[] createUser(String requestBody) {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");
        String name = parameters.getParameter("name");
        String email = parameters.getParameter("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
        return responseView("index");
    }

    private byte[] login(String requestBody, DataOutputStream dos) {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");

        User user = DataBase.findUserById(userId);
        if (user != null && password.equals(user.getPassword())) {
            response302HeaderWithLoginSuccessCookie(dos);
            return responseView("index");
        }

        byte[] body = responseView("user/login_failed");
        response200HeaderWithLoginFailedCookie(dos, body.length);
        return body;
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

    private void response200HeaderWithLoginFailedCookie(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("Set-Cookie: logined=false\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302HeaderWithLoginSuccessCookie(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Set-Cookie: logined=true; Path=/\r\n");
            dos.writeBytes("Location: /index.html\r\n");
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
