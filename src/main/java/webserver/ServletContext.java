package webserver;

import controller.*;

import java.util.*;

public class ServletContext {

    private static Map<Condition, Controller> controllers = new HashMap<>();
    private static Controller NOT_FOUND = (ignore, response) -> response.notFound();

    static {
        controllers.put(UserCreateController::isMapping, new UserCreateController());
        controllers.put(UserListController::isMapping, new UserListController());
        controllers.put(UserLoginController::isMapping, new UserLoginController());
        controllers.put(StaticResourceController::isMapping, new StaticResourceController());
        controllers.put(TemplateResourceController::isMapping, new TemplateResourceController());
    }

    Controller mapping(Request request) {
        return controllers.entrySet().stream()
                .filter(it -> it.getKey().isMapping(request))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(NOT_FOUND);
    }
}