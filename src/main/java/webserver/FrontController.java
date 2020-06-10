package webserver;

import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;
import webserver.controller.Controller;
import webserver.controller.HtmlController;
import webserver.controller.StaticResourceController;
import webserver.exceptions.ErrorMessage;
import webserver.exceptions.WebServerException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FrontController {

    private static final Map<String, Controller> controllers;
    static {
        final Map<String, Controller> temp = new HashMap<>();
        temp.put("^[\\S]+(\\.css|\\.js|\\.ttf|\\.woff)", StaticResourceController.getInstance());
        temp.put("^[\\S]+(\\.html|\\.ico)", HtmlController.getInstance());
        temp.put("/user/create", UserCreateController.getInstance());
        temp.put("/user/login", UserLoginController.getInstance());
        temp.put("/user/list", UserListController.getInstance());
        controllers = Collections.unmodifiableMap(temp);
    }

    public static Controller controllerMapping(String path) {
        for(String regex : controllers.keySet()) {
            if (Pattern.matches(regex, path)) {
                return controllers.get(regex);
            }
        }
        throw new WebServerException(ErrorMessage.REQUEST_PROCESSABLE_CONTROLLER_NOT_FOUND);
    }
}
