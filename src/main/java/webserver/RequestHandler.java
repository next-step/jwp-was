package webserver;

import http.controller.Controller;
import http.controller.Controllers;
import http.controller.user.CreateUserController;
import http.controller.user.LoginController;
import http.controller.user.UserListController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            HttpRequest request = HttpRequest.parse(in);
            HttpResponse response = new HttpResponse();

            Controllers controllers = addControllers();
            if (controllers.containsKey(request.getPath())) {
                Controller controller = controllers.get(request.getPath());
                controller.service(request, response);
            }

            ResponseHandler responseHandler = new ResponseHandler(out, response);
            responseHandler.doResponse(request);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Controllers addControllers() {
        Controllers controllers = new Controllers();
        controllers.addController(new CreateUserController());
        controllers.addController(new LoginController());
        controllers.addController(new UserListController());
        return controllers;
    }
}
