package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.controller.Controller;
import webserver.controller.StaticController;
import webserver.controller.TemplateController;
import webserver.controller.UserCreateController;
import webserver.controller.UserListController;
import webserver.controller.UserLoginController;
import webserver.exception.ApiException;
import webserver.http.SessionStorage;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.response.HttpResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

public class RequestHandler implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private static final SessionStorage SESSION_STORAGE = SessionStorage.empty();
    private static final Set<Controller> CONTROLLERS;

    static {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        CONTROLLERS = Set.of(
                new UserCreateController(),
                new UserListController(new Handlebars(loader)),
                new UserLoginController(SESSION_STORAGE),
                new TemplateController(),
                new StaticController()
        );
    }

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        LOGGER.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            HttpResponse response = matchedResponse(httpRequest(br));
            writeResponse(response, dos);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private HttpRequest httpRequest(BufferedReader br) throws IOException {
        HttpRequest request = HttpRequest.of(SESSION_STORAGE, IOUtils.readLines(br));
        if (request.contentLength() > 0) {
            return request.withBody(RequestBody.from(IOUtils.readData(br, request.contentLength())));
        }
        return request;
    }

    private HttpResponse matchedResponse(HttpRequest request) {
        return CONTROLLERS.stream()
                .filter(controller -> controller.isMatch(request))
                .findAny()
                .map(controller -> executeSafety(controller, request))
                .orElse(HttpResponse.notFound());
    }

    private HttpResponse executeSafety(Controller controller, HttpRequest request) {
        try {
            return controller.execute(request);
        } catch (ApiException e) {
            return HttpResponse.of(e.getCode(), e.getHeader());
        } catch (Exception e) {
            return HttpResponse.internalServerError();
        }
    }

    private void writeResponse(HttpResponse response, DataOutputStream dos) {
        try {
            dos.writeBytes(String.format("HTTP/1.1 %s \r%n", response.code()));
            writeHeaders(response, dos);
            dos.writeBytes("\r\n");
            dos.write(response.body(), 0, response.contentLength());
            dos.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void writeHeaders(HttpResponse response, DataOutputStream dos) throws IOException {
        for (Map.Entry<String, Object> entry : response.headerEntries()) {
            dos.writeBytes(String.format("%s: %s\r%n", entry.getKey(), entry.getValue()));
        }
    }
}
