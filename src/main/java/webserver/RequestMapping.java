package webserver;

import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.ListUserController;
import webserver.controller.LoginUserController;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;

import java.util.Map;

public class RequestMapping {

    private static Map<String, Controller> postController = Map.of(
            "/user/create", new CreateUserController(),
            "/user/login", new LoginUserController()
    );

    private static Map<String, Controller> getControllers = Map.of(
            "/user/list", new ListUserController()
    );

    public static Controller getController(HttpRequest httpRequest) {
        HttpMethod method = httpRequest.getMethod();
        final String requestUrl = httpRequest.getPath().getPath();
        if (method.isPost()) {
            return postController.get(requestUrl);
        }
        return getControllers.get(requestUrl);
    }
}
