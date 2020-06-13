package http;

import user.ui.CreateUserController;
import user.ui.ListUserController;
import user.ui.LoginController;
import user.ui.UserController;

import java.util.HashMap;
import java.util.Map;

public class MappingControllers {

    private static final Map<String, UserController> mappingControllers;

    static {
        mappingControllers = new HashMap<>();
        mappingControllers.put("/user/list", new ListUserController());
        mappingControllers.put("/user/create", new CreateUserController());
        mappingControllers.put("/user/login", new LoginController());
    }

    public static UserController getController(String path) {
        return mappingControllers.getOrDefault(path, new UserController());
    }
}
