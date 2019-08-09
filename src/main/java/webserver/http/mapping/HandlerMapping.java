package webserver.http.mapping;

import webserver.http.controller.*;
import webserver.http.request.Request;
import webserver.resource.ResourceResolver;
import webserver.view.HandlebarViewResolver;
import webserver.view.HtmlViewResolver;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {

    private Map<String, Controller> controllers;
    private ResourceController resourceController;

    HandlerMapping() {

        controllers = new HashMap<>();
        controllers.put("/index", new MainController<>(new HtmlViewResolver()));
        controllers.put("/user/form", new RegisterFormController<>(new HtmlViewResolver()));
        controllers.put("/user/login", new LoginController<>(new HtmlViewResolver()));
        controllers.put("/user/list", new UserListController<>(new HandlebarViewResolver()));
        controllers.put("/user/create", new CreateUserController());

        resourceController = new ResourceController(new ResourceResolver());
    }

    Controller getController(Request request) {
        return controllers.getOrDefault(request.getPath(), resourceController);
    }
}
