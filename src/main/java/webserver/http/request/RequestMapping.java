package webserver.http.request;

import controller.Controller;
import controller.DefaultController;
import controller.user.CreateUserController;
import controller.user.LoginController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final String USER_CREATE_PATH = "/user/create";
    private static final String USER_LOGIN_PATH = "/user/login";
    private static final String USER_LIST_PATH = "/user/list.html";

    private static final Map<String, Controller> REQUEST_MAPPING = new HashMap<>();

    static {
        REQUEST_MAPPING.put(USER_CREATE_PATH, new CreateUserController());
        REQUEST_MAPPING.put(USER_LOGIN_PATH, new LoginController());
        REQUEST_MAPPING.put(USER_LIST_PATH, new CreateUserController());
    }

    public static Controller mapping(String path) {
        return REQUEST_MAPPING.getOrDefault(path, new DefaultController());
    }
}
