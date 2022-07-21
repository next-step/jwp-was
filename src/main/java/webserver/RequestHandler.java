package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            RequestLine requestLine = new RequestLine(reader.readLine());
            HttpHeaders headers = HttpHeaders.from(reader);

            String requestBody = IOUtils.readData(reader, headers.getContentLength());
            logger.debug("request body = {}", requestBody);
            DataOutputStream dos = new DataOutputStream(out);
            response(requestLine, requestBody, dos);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response(RequestLine requestLine, String requestBody, DataOutputStream dos) throws IOException, URISyntaxException {
        String path = requestLine.getRequestPath();
        HttpMethod method = requestLine.getMethod();
        byte[] body = new byte[0];

        if (method.isGet() && ("/".equals(path) || "/index.html".equals(path))) {
            body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
            response200Header(dos, body.length);
        }

        if (method.isGet() && "/user/form.html".equals(path)) {
            body = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
            response200Header(dos, body.length);
        }

        if (method.isGet() && "/user/login.html".equals(path)) {
            body = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");
            response200Header(dos, body.length);
        }

        if (method.isPost() && "/user/create".equals(path)) {
            createUser(requestBody, dos);
        }

        if (method.isPost() && "/user/login".equals(path)) {
            body = login(requestBody, dos);
        }

        responseBody(dos, body);
    }

    private void createUser(String requestBody, DataOutputStream dos) {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");
        String name = parameters.getParameter("name");
        String email = parameters.getParameter("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
        response302Header(dos);
    }

    private byte[] login(String requestBody, DataOutputStream dos) throws IOException, URISyntaxException {
        Parameters parameters = new Parameters(requestBody);
        String userId = parameters.getParameter("userId");
        String password = parameters.getParameter("password");

        User user = DataBase.findUserById(userId);
        if (user != null && password.equals(user.getPassword())) {
            response302HeaderWithLoginSuccessCookie(dos);
            return FileIoUtils.loadFileFromClasspath("./templates/index.html");
        }

        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html");
        response200HeaderWithLoginFailedCookie(dos, body.length);
        return body;
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
