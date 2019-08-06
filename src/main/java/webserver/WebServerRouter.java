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

/**
 * 기본 라우터
 */
public class WebServerRouter {
    protected static final Map<String, BiFunction<HttpRequest, HttpResponse, Optional<String>>> routerMap = new HashMap<>();
    private static final String ROUTING_KEY_FORMAT = "%s%s";

    public static Optional<String> route(HttpRequest httpRequest, HttpResponse httpResponse) {
        String routeKey = String.format(ROUTING_KEY_FORMAT, httpRequest.getMethod(), httpRequest.getUri().getPath());
        return routerMap.keySet().stream()
                .filter(routeKey::equals)
                .map(routerMap::get)
                .findAny().orElse(ErrorController::notFound)
                .apply(httpRequest, httpResponse);
    }

    public static void add(String path, BiFunction<HttpRequest, HttpResponse, Optional<String>> controllerFunction) {
        routerMap.put(path, controllerFunction);
    }
}
