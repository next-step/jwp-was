package webserver.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.domain.RequestLine;
import webserver.response.GetIndexHtmlResponse;
import webserver.response.GetUserFormHtmlResponse;

public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
    public static final String GET_INDEX_HTML = "/index.html";
    public static final String GET_USER_FORM_HTML = "/user/form.html";
    private final Socket connection;

    public ResponseHandler(Socket connection) {
        this.connection = connection;
    }

    public void run(RequestLine requestLine) throws IOException {
        try (OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            if (requestLine.index().equals(GET_INDEX_HTML)) {
                GetIndexHtmlResponse response = new GetIndexHtmlResponse();
                byte[] body = response.response();
                response200Header(dos, body.length);
                responseBody(dos, body);
                return;
            }

            if (requestLine.index().equals(GET_USER_FORM_HTML)) {
                GetUserFormHtmlResponse response = new GetUserFormHtmlResponse();
                response.response(requestLine);
                response200Header(dos, 0);
                responseBody(dos, new byte[0]);
                return;
            }

            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
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
}
