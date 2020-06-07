package http.controller;

import http.controller.user.CreateUserController;
import http.controller.user.LoginController;
import http.controller.user.UserListController;

import java.util.HashMap;
import java.util.Map;

public class Controllers {
    private Map<String, Controller> controllerMap = new HashMap<>();

    public Controllers() {
        controllerMap.put("/user/create", new CreateUserController());
        controllerMap.put("/user/login", new LoginController());
        controllerMap.put("/user/list.html", new UserListController());
    }

    public Controller get(String key) {
        return controllerMap.get(key);
    }

    public boolean containsKey(String key) {
        return controllerMap.containsKey(key);
    }
}
