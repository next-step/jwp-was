package webserver.http.model;

import webserver.controller.Controller;
import webserver.controller.CreateUserController;
import webserver.controller.ListUserController;
import webserver.controller.LoginController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
    private final Map<String, Controller> controllerMap;

    public HandlerMapping() {
        controllerMap = new HashMap<>();
        initialize();
    }

    private void initialize() {
        controllerMap.put("/user/create", new CreateUserController());
        controllerMap.put("/user/login", new LoginController());
        controllerMap.put("/user/list", new ListUserController());
    }

    public HandlerMapping(Map<String, Controller> controllerMap) {
        this.controllerMap = controllerMap;
    }

    public Map<String, Controller> getControllerMap() {
        return Collections.unmodifiableMap(controllerMap);
    }
}
