package webserver;

import user.controller.UserCreateController;
import user.controller.UserListController;
import user.controller.UserLoginController;
import webserver.controller.Controller;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Map<String, Controller> REQUEST_MAPPING = new HashMap<>();

    static {
        REQUEST_MAPPING.put("/user/create", new UserCreateController());
        REQUEST_MAPPING.put("/user/login", new UserLoginController());
        REQUEST_MAPPING.put("/user/list", new UserListController());
    }

    private RequestMapping() {
        throw new AssertionError();
    }

    public static Controller getController(String path) {
        return REQUEST_MAPPING.get(path);
    }
}
