package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.IndexController;
import controller.ListUserController;
import controller.LoginController;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import service.ResourceService;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

import static webserver.request.RequestMethod.GET;

public class Container {
    private Container() {}

    private static final Map<String, Controller> container = new HashMap<>();

    static {
        container.put("GET /", new IndexController());
        container.put("POST /user/create", new CreateUserController());
        container.put("POST /user/login", new LoginController());
        container.put("GET /user/list.html", new ListUserController());
    }

    public static Response handle(Request request) throws IOException {
        Controller controller = container.get(request.getRequestLineHash());
        if (controller != null) {
            return controller.service(request);
        }
        if (request.getMethod() == GET) {
            return ResourceService.getResource(request);
        }
        return ResponseFactory.createNotImplemented();
    }
}
