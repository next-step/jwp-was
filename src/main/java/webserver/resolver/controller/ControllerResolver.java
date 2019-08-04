package webserver.resolver.controller;

import controller.UserController;
import webserver.controller.Controller;
import webserver.request.HttpRequest;
import webserver.resolver.Resolver;
import webserver.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hspark on 2019-08-05.
 */
public class ControllerResolver implements Resolver {
    private static final Map<String, Controller> CONTROLLERS = new HashMap<>();

    static {
        Controller userController = new UserController();
        CONTROLLERS.put(userController.getRequestUrl(), userController);
    }

    @Override
    public HttpResponse resolve(HttpRequest httpRequest) {
        Controller controller = CONTROLLERS.get(httpRequest.getPath());
        return controller.action(httpRequest);
    }
}
