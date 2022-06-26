package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.RequestRouter;
import webserver.http.controller.Controller;
import webserver.http.controller.LoginController;
import webserver.http.controller.SignUpController;
import webserver.http.controller.UserListController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final RequestRouter requestRouter = new RequestRouter();
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    static {
        requestRouter
                .add("/user/create", new SignUpController())
                .add("/user/login", new LoginController())
                .add("/user/list", new UserListController());
    }

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = new HttpRequest(in);
            final HttpResponse httpResponse = new HttpResponse(out);

            final Controller routedController = requestRouter.getRoutedController(httpRequest);
            routedController.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
