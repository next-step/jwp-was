package webserver;

import controller.Controller;
import controller.DefaultController;
import controller.UserCreateController;
import controller.UserLoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RequestHandler implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private static final Pattern STATIC_PATTERN = Pattern.compile(".+\\.html");
    private static final DefaultController DEFAULT_CONTROLLER = new DefaultController();

    private final Socket connection;
    private final Map<String, Controller> requestMapping = new HashMap<>();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        initRequestMapping();
    }

    private void initRequestMapping() {
        requestMapping.put("/index", new DefaultController());
        requestMapping.put("/user/create", new UserCreateController());
        requestMapping.put("/user/login", new UserLoginController());
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final HttpRequest httpRequest = new HttpRequest(in);
            final HttpResponse httpResponse = new HttpResponse(out, httpRequest);
            handle(httpRequest, httpResponse);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (STATIC_PATTERN.matcher(httpRequest.getPath()).matches()) {
            DEFAULT_CONTROLLER.doGet(httpRequest, httpResponse);
            return;
        }

        if (requestMapping.containsKey(httpRequest.getPath())) {
            final Controller controller = requestMapping.get(httpRequest.getPath());
            if (httpRequest.isGet()) {
                controller.doGet(httpRequest, httpResponse);
                return;
            }
            if (httpRequest.isPost()) {
                controller.doPost(httpRequest, httpResponse);
            }
        }
    }
}
