package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.Controller;
import webserver.controller.UserController;

import java.io.*;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = HttpRequest.of(br);
            HttpResponse httpResponse;
            if (httpRequest.isStaticFileRequest()) {
                httpResponse = HttpResponse.of(httpRequest);
            } else {
                httpResponse = HttpResponse.of();
                Controller userController = new UserController();
                userController.service(httpRequest, httpResponse);
            }

            DataOutputStream dos = new DataOutputStream(out);
            response(dos, httpResponse);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            dos.writeBytes(httpResponse.response200());
            responseHeader(dos, httpResponse.getResponseHeaders());
            responseBody(dos, httpResponse.getBody());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, Map<String, ResponseHeader> responseHeaders) {
        try {
            for (String name : responseHeaders.keySet()) {
                dos.writeBytes(name + ": " + responseHeaders.get(name));
            }
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
