package webserver.http;

import webserver.controller.Controller;
import webserver.controller.LoginController;
import webserver.controller.UserCreateController;
import webserver.controller.UserListController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Map<String, Controller> HANDLERS = new HashMap<>();

    static {
        HANDLERS.put("/user/create", new UserCreateController());
        HANDLERS.put("/user/login", new LoginController());
        HANDLERS.put("/user/list", new UserListController());
    }

    public static Controller getHandler(String requestURI) {
        return HANDLERS.get(requestURI);
    }
}
