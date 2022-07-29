package webserver;

import static http.request.HttpMethod.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.controller.Controller;
import webserver.controller.LoginController;
import webserver.controller.UserCreateController;
import webserver.controller.UserListController;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Map<ControllerIdentity, Controller> CONTROLLERS = Map.of(
        new ControllerIdentity("/user/create", POST), new UserCreateController(),
        new ControllerIdentity("/user/login", POST), new LoginController(),
        new ControllerIdentity("/user/list", GET), new UserListController());

    private final Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream out = connection.getOutputStream(); var bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream))) {
            var httpRequest = HttpRequest.parse(bufferedReader);

            var response = createHttpResponse(httpRequest);

            var dataOutputStream = new DataOutputStream(out);
            response.write(dataOutputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse createHttpResponse(HttpRequest httpRequest) {
        if (httpRequest.isStaticFile()) {
            return HttpResponse.parseStaticFile(httpRequest.getUrl(), httpRequest.getFileExtension());
        }

        var controller = CONTROLLERS.get(new ControllerIdentity(httpRequest.getUrl(), httpRequest.getMethod()));
        return controller.run(httpRequest);
    }
}
