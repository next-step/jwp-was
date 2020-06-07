package webserver;

import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;
import webserver.controller.Controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FrontController {

    private static Map<String, Controller> controllers;
    static {
        Map<String, Controller> temp = new HashMap<>();
        temp.put("/user/create", UserCreateController.getInstance());
        temp.put("/user/login", UserLoginController.getInstance());
        temp.put("/user/list", UserListController.getInstance());
        controllers = Collections.unmodifiableMap(temp);
    }

    public static Controller controllerMapping(String path) {
        Controller controller = controllers.get(path);
        if (controller == null) {
            throw new RuntimeException("처리할 수 있는 컨트롤러가 없음");
        }
        return controller;
    }
}
