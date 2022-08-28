package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.http.domain.RequestBody;
import webserver.http.domain.RequestHeader;
import webserver.http.domain.RequestUrl;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static utils.FileIoUtils.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public static final byte[] HELLO_WORLD = "Hello World".getBytes();

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String url = br.readLine();
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.addRequestHeaders(br);

            RequestUrl requestUrl = new RequestUrl(url);
            byte[] body = HELLO_WORLD;
            DataOutputStream dos = new DataOutputStream(out);

            if (checkGetIndexHtml(requestUrl)) {
                body = loadFileFromClasspath("./templates/index.html");
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else if (checkGetFormHtml(requestUrl)) {
                body = loadFileFromClasspath("./templates/user/form.html");
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else if (checkSignUp(requestUrl)) {
                Map<String, String> bodies = bodies(br, requestHeader);
                String userId = bodies.get("userId");
                DataBase.addUser(new User(userId, bodies.get("password"), bodies.get("name"), bodies.get("email")));
                response302Header(dos, "/index.html");
            } else if (checkLoginHtml(requestUrl)) {
                body = loadFileFromClasspath("./templates/user/login.html");
                response200Header(dos, body.length);
                responseBody(dos, body);
            } else if (checkLogin(requestUrl)) {
                Map<String, String> bodies = bodies(br, requestHeader);
                String userId = bodies.get("userId");
                User user = DataBase.findUserById(userId);

                if (user.getPassword().equals(bodies.get("password"))) {
                    response302Header(dos, "/index.html");
                    responseCookie(dos, "logined", "true");
                    return;
                }
                response302Header(dos, "/login_failed.html");
                responseCookie(dos, "logined", "false");
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean checkGetIndexHtml(RequestUrl requestUrl) throws IOException, URISyntaxException {
        return requestUrl.isGetMethod() && requestUrl.samePath("/index.html");
    }

    private boolean checkGetFormHtml(RequestUrl requestUrl) throws IOException, URISyntaxException {
        return requestUrl.isGetMethod() && requestUrl.samePath("/user/form.html");
    }

    private boolean checkSignUp(RequestUrl requestUrl) throws IOException, URISyntaxException {
        return requestUrl.isPostMethod() && requestUrl.samePath("/user/create");
    }

    private boolean checkLoginHtml(RequestUrl requestUrl) throws IOException, URISyntaxException {
        return requestUrl.isGetMethod() && requestUrl.samePath("/user/login.html");
    }

    private boolean checkLogin(RequestUrl requestUrl) throws IOException {
        return requestUrl.isPostMethod() && requestUrl.samePath("/user/login");
    }

    private Map<String, String> bodies(BufferedReader br, RequestHeader requestHeader) throws IOException {
        String body = IOUtils.readData(br, Integer.parseInt(requestHeader.getValue("Content-Length")));
        RequestBody requestBody = new RequestBody(body);
        return requestBody.bodies();
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

    private void responseCookie(DataOutputStream dos, String key, String value) {
        try {
            String cookie = "Set-Cookie: " + key + "=" + value + "; Path=/";
            logger.debug(cookie);
            dos.writeBytes(cookie);
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
