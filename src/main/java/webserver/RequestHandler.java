package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;

import java.io.*;
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
            DataOutputStream dos = new DataOutputStream(out);

            final HttpRequest httpRequest = new HttpRequest(in);

            if (this.isSignUpRequest(httpRequest)) {
                this.handleSignUpRequest(httpRequest);;
            }

            byte[] body = FileIoUtils.loadFileFromClasspath("templates/" + httpRequest.getPath());

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isSignUpRequest(final HttpRequest httpRequest) {
        return httpRequest.getMethod().equals(HttpMethod.POST) && httpRequest.getPath().equals("/user/create");
    }

    private void handleSignUpRequest(final HttpRequest httpRequest) {
        final RequestBody requestBody = httpRequest.getBody();

        final String userId = requestBody.get("userId");
        final String password = requestBody.get("password");
        final String name = requestBody.get("name");
        final String email = requestBody.get("email");

        DataBase.addUser(new User(userId, password, name, email));
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
}
