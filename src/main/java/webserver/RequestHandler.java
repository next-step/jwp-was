package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.Contents;
import webserver.http.HttpBody;
import webserver.http.HttpRequest;
import webserver.http.Path;
import webserver.user.UserFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

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
            HttpRequest httpRequest = new HttpRequest(in);

            Path path = httpRequest.getRequestLine().getPath();
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = null;

            if (path.isSameUrlPath("/user/create")) {
                final User user = UserFactory.from(httpRequest);
                DataBase.addUser(user);
                response302Header(dos);
                return;
            }
            if (path.isSameUrlPath("/user/login")) {
                final HttpBody httpBody = httpRequest.getHttpBody();
                final Contents contents = httpBody.getContents();
                final String userId = contents.getContent("userId");
                final User user = DataBase.findUserById(userId);
                if (user == null) {
                    responseResource(out, "/user/login_failed.html");
                    return;
                }
                String password = httpBody.getContents().getContent("password");
                if (user.isSamePassword(password)) {
                    responseLoginSuccess(dos);
                    return;
                }
                responseResource(out, "/user/login_failed.html");
                return;
            }

            if (path.endWith("html") || path.endWith("ico")) {
                body = FileIoUtils.loadFileFromClasspath("./templates" + path.getPath());
            }
            if (path.endWith("css") || path.endWith("js")) {
                body = FileIoUtils.loadFileFromClasspath("./static" + path.getPath());
            }
            if (body == null) {
                logger.error("not found: {}", path.getPath());
            }
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseLoginSuccess(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Set-Cookie: logined=true \r\n");
            dos.writeBytes("Location: / \r\n");
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 REDIRECT \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseResource(OutputStream out, String url) throws IOException, URISyntaxException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + url);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }
}
