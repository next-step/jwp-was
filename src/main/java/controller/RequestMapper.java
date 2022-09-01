package controller;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    public static final Map<String, Controller> REQUEST_PATHS = new HashMap<>();

    static {
        REQUEST_PATHS.put("/user/create", new UserCreateController());
        REQUEST_PATHS.put("/user/login", new LoginController());
        REQUEST_PATHS.put("/user/list", new UserListController());
    }

    public Controller getController(String path) {
        return REQUEST_PATHS.getOrDefault(path, new StaticController());
    }
}
