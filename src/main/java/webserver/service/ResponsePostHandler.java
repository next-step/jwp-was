package webserver.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import webserver.header.request.RequestHeader;
import webserver.header.request.requestline.RequestLine;
import webserver.response.post.PostUserCreateResponse;

public class ResponsePostHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponsePostHandler.class);
    private static final String USER_CREATE = "/user/create";
    private final Socket connection;

    public ResponsePostHandler(Socket connection) {
        this.connection = connection;
    }

    public void run(RequestHeader header, RequestLine requestLine, String requestBody) {
        try (OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            if (requestLine.index().equals(USER_CREATE)) {
                PostUserCreateResponse response = new PostUserCreateResponse();
                response.response(requestBody);
                byte[] body = "".getBytes();
                response302Header(dos, header, body);
                responseBody(dos, body);
                System.out.println("redirect: Location: " + header.host() + "/index.html");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, RequestHeader header, byte[] body) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + "http://" + header.host() + "/index.html\r\n");
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
