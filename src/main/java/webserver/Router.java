package webserver;

import controller.ErrorController;
import controller.UserController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class Router {
    private static final String GET_METHOD = "GET ";
    private static final String POST_METHOD = "POST ";
    private static final Map<String, BiFunction<HttpRequest, HttpResponse, Optional<String>>> routerMap = new HashMap<>();
    static {
        routerMap.put(POST_METHOD + "/user/create", UserController::createUser);
        routerMap.put(POST_METHOD + "/user/login", UserController::login);
        routerMap.put(GET_METHOD + "/user/list", UserController::list);
    }

    public static Optional<String> route(HttpRequest httpRequest, HttpResponse httpResponse) {
        String routeKey = String.format("%s %s", httpRequest.getMethod(), httpRequest.getUri().getPath());
        return routerMap.keySet().stream()
                .filter(routeKey::equals)
                .map(routerMap::get)
                .findAny().orElse(ErrorController::notFound)
                .apply(httpRequest, httpResponse);
    }
}
