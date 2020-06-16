package controller;

import controller.login.UserLoginController;
import controller.user.UserCreateController;
import controller.user.UserListController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Map<String, Controller> Mapper = new HashMap<>();

    static {
        Mapper.put("/user/create",  new UserCreateController());
        Mapper.put("/user/login",   new UserLoginController());
        Mapper.put("/user/list",    new UserListController());
    }

    public static Controller getController(String path) {
        return Mapper.get(path);
    }

}
