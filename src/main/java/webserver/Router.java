package webserver;

import controller.ErrorController;
import controller.UserController;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class Router {
    private static final String ROUTING_KEY_FORMAT = "%s%s";
    private static final Map<String, BiFunction<HttpRequest, HttpResponse, Optional<String>>> routerMap = new HashMap<>();
    static {
        routerMap.put(HttpMethod.POST + "/user/create", UserController::createUser);
        routerMap.put(HttpMethod.POST + "/user/login", UserController::login);
        routerMap.put(HttpMethod.GET + "/user/list", UserController::list);
    }

    public static Optional<String> route(HttpRequest httpRequest, HttpResponse httpResponse) {
        String routeKey = String.format(ROUTING_KEY_FORMAT, httpRequest.getMethod(), httpRequest.getUri().getPath());
        return routerMap.keySet().stream()
                .filter(routeKey::equals)
                .map(routerMap::get)
                .findAny().orElse(ErrorController::notFound)
                .apply(httpRequest, httpResponse);
    }
}
