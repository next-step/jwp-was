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
        Controller userSignupController = new UserSignupController();
        Controller userLoginController = new UserLoginController();
        Controller userListController = new UserListController();
        CONTROLLERS.put(userSignupController.getRequestUrl(), userSignupController);
        CONTROLLERS.put(userLoginController.getRequestUrl(), userLoginController);
        CONTROLLERS.put(userListController.getRequestUrl(), userListController);
    }

    @Override
    public void resolve(HttpRequest httpRequest, HttpResponse httpResponse) {
        Controller controller = CONTROLLERS.get(httpRequest.getPath());
        controller.action(httpRequest, httpResponse);
    }
}
