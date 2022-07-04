package webserver;

import utils.ResourceUtils;
import webserver.http.controller.Controller;
import webserver.http.controller.LoginController;
import webserver.http.controller.NotFoundController;
import webserver.http.controller.ResourceController;
import webserver.http.controller.UserCreateController;
import webserver.http.controller.UserListController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandlerMapping {

    private static final Set<String> applyFileExtension = ResourceUtils.resourcePath.keySet();

    private static final Controller NOT_FOUND = new NotFoundController();

    protected static Map<String, Controller> handlers = new HashMap<>();

    static {
        handlers.put("/user/login", new LoginController());
        handlers.put("/user/create", new UserCreateController());
        handlers.put("/user/list", new UserListController());
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
