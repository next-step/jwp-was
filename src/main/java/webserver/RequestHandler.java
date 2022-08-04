package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.controller.UserCreateController;
import user.controller.UserListController;
import user.controller.UserLoginController;
import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestParser;
import webserver.http.HttpResponse;
import webserver.http.HttpResponseWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private static final Map<String, Controller> requestMapping = new HashMap<>();

    private final Socket connection;

    static {
        requestMapping.put("/user/create", new UserCreateController());
        requestMapping.put("/user/login", new UserLoginController());
        requestMapping.put("/user/list", new UserListController());
    }

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = HttpRequestParser.parse(in);
            final HttpResponse httpResponse = handle(httpRequest);
            new HttpResponseWriter(out).write(httpResponse);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private HttpResponse handle(HttpRequest httpRequest) {
        if (requestMapping.containsKey(httpRequest.getPath())) {
            final Controller controller = requestMapping.get(httpRequest.getPath());
            return controller.service(httpRequest);
        }
        return HttpResponse.forward(httpRequest.getPath());
    }
}
