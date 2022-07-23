package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.Controller;
import webserver.controller.LoginController;
import webserver.controller.UserCreateController;
import webserver.controller.UserListController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Map<String, Controller> requestMapping = new HashMap<>();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        initRequestMapping();
    }

    private void initRequestMapping() {
        requestMapping.put("/user/create", new UserCreateController());
        requestMapping.put("/user/login", new LoginController());
        requestMapping.put("/user/list", new UserListController());
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
        String path = getPath(request.getRequestURI());
        Controller controller = requestMapping.get(path);

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
