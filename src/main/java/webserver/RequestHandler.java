package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.RequestMapping;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);
            handle(request, response);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String sessionId = request.getSessionId();

        if (sessionId == null) {
            response.addCookie("JSESSIONID", UUID.randomUUID().toString());
        }

        String path = getPath(request.getRequestURI());
        Controller controller = RequestMapping.getHandler(path);

        if (controller == null) {
            response.forward(path);
            return;
        }

        controller.handle(request, response);
    }

    private String getPath(String requestURI) {
        if (requestURI.equals("/")) {
            return "/index.html";
        }
        return requestURI;
    }
}
