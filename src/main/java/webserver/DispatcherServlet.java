package webserver;

import controller.*;
import java.util.HashMap;
import java.util.Map;
import request.HttpRequest;

public class DispatcherServlet {

    private static Map<String, AbstractController> controllerMap = new HashMap<>();

    static {
        controllerMap.put("/user/create", new UserCreateController());
        controllerMap.put("/user/login", new LoginController());
        controllerMap.put("/user/list", new UserListController());
    }

    public static AbstractController isMatcher(String path) {
        return controllerMap.getOrDefault(path, new DefaultController());
    }

}
