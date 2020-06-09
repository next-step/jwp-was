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

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginUserController());
        controllers.put("/user/list", new ListUserController());
    }

    public static Controller getController(String path) {
        return controllers.get(path);
    }
}
