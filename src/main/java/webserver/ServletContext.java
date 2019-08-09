package webserver;

import controller.*;
import webserver.response.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {

    private static Map<Condition, Controller> controllers = new HashMap<>();
    private static Response INTERNAL_SERVER_ERROR = HttpResponse.internalServerError();

    static {
        controllers.put(UserCreateController::isMapping, new UserCreateController());
        controllers.put(UserListController::isMapping, new UserListController());
        controllers.put(UserLoginController::isMapping, new UserLoginController());
        controllers.put(StaticResourceController::isMapping, new StaticResourceController());
        controllers.put(TemplateResourceController::isMapping, new TemplateResourceController());
    }

    Response mapping(Request request) {
        return controllers.entrySet().stream()
                .filter(it -> it.getKey().isMapping(request))
                .map(Map.Entry::getValue)
                .findFirst()
                .map(controller -> serve(request, controller))
                .orElseGet(HttpResponse::notFound);
    }

    private Response serve(Request request, Controller controller) {
        try {
            return controller.service(request);
        } catch (Exception e) {
            return INTERNAL_SERVER_ERROR;
        }
    }
}