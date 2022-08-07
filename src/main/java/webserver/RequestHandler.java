package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mvc.controller.Controller;
import mvc.controller.ControllerMatcher;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String ROOT = "/";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.from(in);
            HttpResponse response = new HttpResponse(out);
            String path = getDefaultPath(request.getPath());
            Controller controller = ControllerMatcher.matchController(path);
            if (controller == null) {
                response.notFound();
                response.writeResponse();
                return;
            }
            controller.service(request, response);
            response.writeResponse();
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String getDefaultPath(String path) {
        if (ROOT.equals(path)) {
            return "/index.html";
        }
        return path;
    }
}
