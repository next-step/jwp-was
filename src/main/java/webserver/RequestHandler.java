package webserver;

import controller.Controller;
import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            if (httpRequest.isGet()) {
                return controller.doGet(httpRequest);
            }
            if (httpRequest.isPost()) {
                return controller.doPost(httpRequest);
            }
        }
        return HttpResponse.forward(httpRequest.getPath());
    }
}
