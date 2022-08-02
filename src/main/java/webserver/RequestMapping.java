package webserver;

import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.ListUserController;
import webserver.controller.LoginUserController;
import webserver.http.Path;

import java.util.Map;

public class RequestMapping {

    private static Map<String, Controller> controllers = Map.of(
            "/user/create", new CreateUserController(),
            "/user/list", new ListUserController(),
            "/user/login", new LoginUserController()
    );

    public static Controller getController(Path path) {
        final String requestUrl = path.getPath();
        return controllers.get(requestUrl);
    }
}
