package webserver;

import controller.ErrorController;
import controller.UserController;
import webserver.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class Router {
    private static final String GET_METHOD = "GET ";
    private static final String POST_METHOD = "POST ";
    private static final Map<String, Function<HttpRequest, Optional<Object>>> routerMap = new HashMap<>();
    static {
        routerMap.put(POST_METHOD + "/user/create", UserController::createUser);
    }

    public static Function<HttpRequest, Optional<Object>> route(HttpRequest httpRequest) {
        String routeKey = String.format("%s %s", httpRequest.getMethod(), httpRequest.getUri().getPath());
        return routerMap.keySet().stream()
                .filter(routeKey::equals)
                .map(routerMap::get)
                .findAny().orElse(ErrorController::notFound);
    }
}
