package webserver.adapter.in;

import webserver.adapter.in.controller.Controller;
import webserver.adapter.in.controller.CreateUserController;
import webserver.adapter.in.controller.ListUserController;
import webserver.adapter.in.controller.LoginController;
import webserver.application.UserProcessor;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    private final static String USER_LOGIN_PATH = "/user/login";
    private final static String USER_CREATE_PATH = "/user/create";
    private final static String USER_LIST_PATH = "/user/list.html";

    private final Map<String, Controller> controllerMap;

    public RequestMapper(UserProcessor userProcessor) {
        Map<String, Controller> map = new HashMap<>();
        map.put(USER_LOGIN_PATH, new LoginController(userProcessor));
        map.put(USER_CREATE_PATH, new CreateUserController(userProcessor));
        map.put(USER_LIST_PATH, new ListUserController(userProcessor));

        this.controllerMap = map;
    }

    public Controller getController(String path) {
        return controllerMap.get(path);
    }
}
