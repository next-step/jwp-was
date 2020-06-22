package webserver.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerInitializer {

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new UserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new UserListController());
    }

    public static Controller get(String host) {
        return controllers.get(host);
    }
}
