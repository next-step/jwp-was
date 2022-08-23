package webserver;

import controller.Controller;
import controller.CreateUserController;
import controller.ListUserController;
import controller.LoginController;

import java.util.Map;

public class RequestMapper {

    private static Map<String, Controller> controllers = Map.of(
            "/user/create", new CreateUserController(),
            "/user/list", new ListUserController(),
            "/user/login", new LoginController()
    );

    public static Controller from(String path) {
        return controllers.get(path);
    }

}
