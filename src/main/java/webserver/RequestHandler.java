package webserver;

import http.controller.Controller;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

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
            HttpRequest httpRequest = HttpRequest.getInstance(in);
            HttpResponse response = Controller.requestMapping(httpRequest);

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, response);
            responseBody(dos, response);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, HttpResponse response) {
        try {
            dos.writeBytes("HTTP/1.1 " + response.getStatusCode() + " " + response.getStatusName() + " \r\n");
            dos.writeBytes("Content-Type: " + response.getContentType() + "\r\n");
            dos.writeBytes("Content-Length: " + response.getContentLength() + "\r\n");

            for (Map.Entry<String, String> headerEntry : response.getCustomHeader().entrySet()) {
                String key = headerEntry.getKey();
                String value = headerEntry.getValue();
                dos.writeBytes(key + ": " + value + "\r\n");
            }

            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, HttpResponse response) {
        try {
            dos.write(response.getBody(), 0, response.getContentLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
