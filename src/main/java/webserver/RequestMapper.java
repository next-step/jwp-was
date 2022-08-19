package webserver;

import controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    public static final Map<String, Controller> CONTROLLERS = new HashMap<>();
    public static final Controller DEFAULT_CONTROLLER = new StaticResourceController();

    static {
        CONTROLLERS.put("/user/create", new CreateUserController());
        CONTROLLERS.put("/user/login", new LoginController());
        CONTROLLERS.put("/user/list", new ListUserController());
    }

    public Controller getController(String path) {
        return CONTROLLERS.getOrDefault(path, DEFAULT_CONTROLLER);
    }
}
