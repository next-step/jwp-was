package http.request;

import com.google.common.collect.ImmutableMap;
import http.controller.*;

public class RequestMapper {
    private static final String PATH_CONTROLLER_STRING = ".html";
    private static final PathController pathController = new PathController();
    private static final StaticController staticController = new StaticController();
    private static final ImmutableMap<String, Controller> CONTROLLER_MAP = ImmutableMap.<String, Controller>builder()
            .put("/user/create", new UserCreateController())
            .put("/user/list", new UserListController())
            .put("/user/login", new UserLoginController())
            .build();

    public static Controller getController(final String path) {
        if (CONTROLLER_MAP.containsKey(path)) {
            return CONTROLLER_MAP.get(path);
        }
        if (path.endsWith(PATH_CONTROLLER_STRING)) {
            return pathController;
        }
        return staticController;
    }
}
