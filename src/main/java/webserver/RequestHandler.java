package webserver;

import controller.Controller;
import controller.UserCreateController;
import controller.UserListController;
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
    public static final Pattern TEMPLATES_PATTERN = Pattern.compile("(.+).(htm|html)");
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
            final HttpRequest httpRequest = new HttpRequest(in);
            final HttpResponse httpResponse = new HttpResponse(out, httpRequest);
            handle(httpRequest, httpResponse);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (requestMapping.containsKey(httpRequest.getPath())) {
            final Controller controller = requestMapping.get(httpRequest.getPath());
            if (httpRequest.isGet()) {
                controller.doGet(httpRequest, httpResponse);
            }
            if (httpRequest.isPost()) {
                controller.doPost(httpRequest, httpResponse);
            }
            return;
        }
        forwardNotMapped(httpRequest, httpResponse);
    }

    private void forwardNotMapped(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (TEMPLATES_PATTERN.matcher(httpRequest.getPath()).matches()) {
            httpResponse.forwardTemplate(httpRequest.getPath());
            return;
        }
        httpResponse.forwardStatic(httpRequest.getPath());
    }
}
