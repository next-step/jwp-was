package webserver;

import service.UserService;
import webserver.controller.Controller;
import webserver.controller.ListUserController;
import webserver.controller.LoginController;
import webserver.controller.RegistrationController;

import java.util.HashMap;

public class ComponentFactory {
    public static HashMap<String, Controller> getComponents () {
        return new HashMap<String, Controller>() {{
            put("/user/create", new RegistrationController(userService));
            put("/user/login", new LoginController(userService));
            put("/user/list", new ListUserController(userService));
        }};
    }

    public static UserService userService = new UserService();

}
