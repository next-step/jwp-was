package webserver;

import http.controller.Controller;
import http.controller.CreateUserController;
import http.controller.ListUserController;
import http.controller.LoginUserController;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class RequestMapping {

    private static final String CREATE_CONTROLLER_PATH = "/user/create";
    private static final String LOGIN_CONTROLLER_PATH = "/user/login";
    private static final String LIST_CONTROLLER_PATH = "/user/list";

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put(CREATE_CONTROLLER_PATH, new CreateUserController());
        controllers.put(LOGIN_CONTROLLER_PATH, new LoginUserController());
        controllers.put(LIST_CONTROLLER_PATH, new ListUserController());
    }

    public static Controller getController(String path) {
        return controllers.get(path);
    }
}
