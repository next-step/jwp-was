package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            HttpRequest httpRequest = new HttpRequest(in);
            String path = httpRequest.getPath();

            DataOutputStream dos = new DataOutputStream(out);
            String fileExtension = FileIoUtils.getFileExtension(path);
            String contentType = ContentType.selectContent(fileExtension);

            byte[] body = "Hello World22".getBytes();
            if (path.endsWith(".html")) {
                body = FileIoUtils.loadFileFromClasspath("./templates"+path);
            } else if (path.equals("/user/create")) {

                Map<String, String> parameters = httpRequest.getBody().getParameter();
                createUser(parameters, dos);

                return;
            } else if (path.equals("/user/login")) {
                Map<String, String> parameters = httpRequest.getBody().getParameter();
                loginUser(parameters, dos);

                return;
            } else if (path.equals("/user/list")) {
                listUser(httpRequest.getCookie(), dos, contentType);

                return;
            }



            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getParameters(BufferedReader br, HttpHeader httpHeader) throws IOException {
        final RequestBody requestBody = new RequestBody();
        requestBody.toBody(br, httpHeader);
        return requestBody.createParameter(requestBody.getBody());
    }

    public void createUser(Map<String, String> queryString, DataOutputStream dos) {
        User user = new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );

        DataBase.addUser(user);
        logger.debug("createdUserId : {}", user.getUserId());

        response302Header(dos, "/index.html");

    }

    public void loginUser(Map<String, String> parameters, DataOutputStream dos) {
        User loginUser = DataBase.findUserById(parameters.get("userId"));
        boolean isLogin = !Objects.isNull(loginUser) && parameters.get("password").equals(loginUser.getPassword());
        response302Header(dos, isLogin ? "/index.html" : "/login_failed.html", setCookie(String.valueOf(isLogin)));
    }

    public void listUser(Cookie cookie, DataOutputStream dos, String contentType) throws IOException {

        if (!Boolean.parseBoolean(cookie.getValue("logined"))) {
            response302Header(dos, "/login.html");
            return;
        }

        List<User> users = new ArrayList<>(DataBase.findAll());
        byte[] loaded = HandleBarsTemplate.load("user/list", users).getBytes();

        response200Header(dos, loaded.length, contentType);
        responseBody(dos, loaded);

        return;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, final String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    private void response302Header(DataOutputStream dos, final String location, final String setCookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes(setCookie);
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

    private String setCookie(String bool) {
        return "Set-Cookie: " + "logined=" + bool + "; " + "Path" + "=" + "/";
    }
}
