package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.StaticResourceController;
import webserver.http.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class FrontController {

    private static final Map<String, Controller> CONTROLLERS = new HashMap<>();

    static {
        CONTROLLERS.put("/user/create", new CreateUserController());
    }

    public static Controller findMatchController(final HttpRequest httpRequest) {
        return CONTROLLERS.getOrDefault(httpRequest.getPathValue(), new StaticResourceController());
    }
}
