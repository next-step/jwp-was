package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
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

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            RequestLine requestLine = RequestLine.parse(line);
            logger.debug("request line : {}", requestLine);

            String path = requestLine.getPath().getPath();
            logger.debug("request path : {}", path);

            HttpMethod method = requestLine.getMethod();
            logger.debug("request method : {}", method);

            HttpHeader httpHeader = new HttpHeader();
            while (!"".equals(line)) {
                line = br.readLine();
                logger.debug("header : {}", line);
                httpHeader.addHeader(line);
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World22".getBytes();
            if (path.endsWith(".html")) {
                body = FileIoUtils.loadFileFromClasspath("./templates"+path);
            } else if (path.equals("/user/create")) {

                Map<String, String> parameters = getParameters(br, httpHeader);
                createUser(parameters, dos);

                return;
            } else if (path.equals("/user/login")) {
                Map<String, String> parameters = getParameters(br, httpHeader);
                loginUser(parameters, dos);

                return;
            }

            response200Header(dos, body.length);
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
        return requestBody.getParameters(requestBody.getBody());
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

    public void listUser() {

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
