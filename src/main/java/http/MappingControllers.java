package http;

import user.ui.CreateUserController;
import user.ui.ListUserController;
import user.ui.LoginController;
import user.ui.UserController;

import java.util.HashMap;
import java.util.Map;

public class MappingControllers {
    private static final String USER_LIST_URL = "/user/list";
    private static final String USER_CREATE_URL = "/user/create";
    private static final String USER_LOGIN_URL = "/user/login";


    private static final Map<String, UserController> mappingControllers;

    static {
        mappingControllers = new HashMap<>();
        mappingControllers.put(USER_LIST_URL, new ListUserController());
        mappingControllers.put(USER_CREATE_URL, new CreateUserController());
        mappingControllers.put(USER_LOGIN_URL, new LoginController());
    }

    public static UserController getController(String path) {
        return mappingControllers.getOrDefault(path, new UserController());
    }
}
