package webserver;

import controller.Controller;
import controller.DefaultController;
import controller.LoginController;
import controller.UserCreateController;
import controller.UserListController;
import java.util.HashMap;
import java.util.Map;
import request.HttpRequest;

public class DispatcherServlet {

    private static Map<String, Controller> controllerMap = new HashMap<>();

    static {
        controllerMap.put("/user/create", new UserCreateController());
        controllerMap.put("/user/login", new LoginController());
        controllerMap.put("/user/list", new UserListController());
    }

    public static Controller isMatcher(String path) {
        return controllerMap.getOrDefault(path, new DefaultController());
    }

}
