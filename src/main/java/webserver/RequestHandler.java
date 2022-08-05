package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.HttpRequest;
import http.request.session.MemorySessionStore;
import webserver.controller.LoginController;
import webserver.controller.Router;
import webserver.controller.UserCreateController;
import webserver.controller.UserListController;

public class RequestHandler implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private static final Router ROUTER = new Router(Map.of(
        new ControllerIdentity("/user/create"), new UserCreateController(),
        new ControllerIdentity("/user/login"), new LoginController(),
        new ControllerIdentity("/user/list"), new UserListController()));

    public static final MemorySessionStore STORE = new MemorySessionStore(new ConcurrentHashMap<>());

    private final Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream out = connection.getOutputStream(); var bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream))) {
            var httpRequest = HttpRequest.parse(bufferedReader, STORE);

            var response = ROUTER.handle(httpRequest);
            if (response.isMarkdown()) {
                response.putCookie(HttpRequest.SESSION_KEY, httpRequest.currentClientUserId());
            }

            var dataOutputStream = new DataOutputStream(out);
            response.write(dataOutputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
