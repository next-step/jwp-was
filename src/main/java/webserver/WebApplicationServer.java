package webserver;

import static http.request.HttpMethod.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.controller.Controller;
import webserver.controller.LoginController;
import webserver.controller.UserCreateController;
import webserver.persistence.Users;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        var users = new Users();
        Map<Resource, Controller> controllers = Map.of(
            new Resource("/user/create", POST), new UserCreateController(users),
            new Resource("/user/login", POST), new LoginController(users)
        );

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection, controllers));
                thread.start();
            }
        }
    }
}
