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
import webserver.controller.IndexController;
import webserver.controller.UserCreateController;
import webserver.controller.UserFormController;
import webserver.controller.UserListController;
import webserver.controller.UserLoginController;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Map<String, Controller> requestMapping = new HashMap<>();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        initRequestMapping();
    }

    private void initRequestMapping() {
        requestMapping.put("/", new IndexController());
        requestMapping.put("/index.html", new IndexController());
        requestMapping.put("/user/form.html", new UserFormController());
        requestMapping.put("/user/create", new UserCreateController());
        requestMapping.put("/user/login.html", new UserLoginController());
        requestMapping.put("/user/login", new UserLoginController());
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
        RequestLine requestLine = request.getRequestLine();
        String path = requestLine.getPath();
        HttpHeaders headers = request.getHeaders();

        Controller controller = requestMapping.get(path);
        if (controller != null) {
            controller.handle(request, response);
            return;
        }

        response.addHeader("Content-Type", headers.getAccept());
        response.forward(path);
    }
}
