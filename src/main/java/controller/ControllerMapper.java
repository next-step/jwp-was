package controller;

import controller.login.LoginController;
import controller.user.UserCreateController;
import controller.user.UserListController;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapper {

    private static final Map<String, Controller> MAPPER = new HashMap<>();

    static {
        MAPPER.put("/user/create", new UserCreateController());
        MAPPER.put("/user/login", new LoginController());
        MAPPER.put("/user/list", new UserListController());
    }

    public Controller findController(String path) {
        return MAPPER.get(path);
    }
}
