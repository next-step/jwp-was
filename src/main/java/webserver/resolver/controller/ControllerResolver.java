package webserver.resolver.controller;

import requesthandler.UserListController;
import requesthandler.UserLoginController;
import requesthandler.UserSignupController;
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
        CONTROLLERS.put(UserSignupController.URL, new UserSignupController());
        CONTROLLERS.put(UserLoginController.URL, new UserLoginController());
        CONTROLLERS.put(UserListController.URL, new UserListController());
    }

    @Override
    public void resolve(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = CONTROLLERS.get(httpRequest.getPath());
        controller.action(httpRequest, httpResponse);
    }
}
