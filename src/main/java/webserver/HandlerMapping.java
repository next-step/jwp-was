package webserver;

import webserver.http.controller.Controller;
import webserver.http.controller.LoginController;
import webserver.http.controller.NotFoundController;
import webserver.http.controller.ResourceController;
import webserver.http.controller.UserCreateController;
import webserver.http.controller.UserListController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerMapping {

    private static final List<String> applyFileExtension = List.of(".html", ".css", ".ico", ".js");

    private static final Controller NOT_FOUND = new NotFoundController();

    private static Map<String, Controller> handlers = new HashMap<>();

    static {
        handlers.put("/user/login", new LoginController());
        handlers.put("/user/create", new UserCreateController());
        handlers.put("/user/list", new UserListController());
        handlers.put("/user/login", new LoginController());
        handlers.put("resource", new ResourceController());
    }

    public Controller getHandler(String path) {
        boolean isResource = applyFileExtension.stream()
                                      .anyMatch(extension -> path.endsWith(extension));
        if (isResource) {
            return handlers.get("resource");
        }

        return handlers.getOrDefault(path, NOT_FOUND);
    }
}
