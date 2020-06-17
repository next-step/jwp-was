package webserver;

import java.io.*;
import java.net.Socket;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.request.RequestMapper;
import http.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            final HttpRequest httpRequest = HttpRequest.from(in);
            final HttpResponse httpResponse = HttpResponse.of(httpRequest, out);
            final Controller controller = RequestMapper.getController(httpRequest.getPath());
            controller.service(httpRequest, httpResponse);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
