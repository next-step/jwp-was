package webserver;

import controller.ErrorController;
import request.RequestMap;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Optional;

public class RequestMapping {
    private static final String REQUEST_KEY_FORMAT = "%s%s";

    public static Optional<String> mapping(HttpRequest httpRequest, HttpResponse httpResponse) {
        String routeKey = String.format(REQUEST_KEY_FORMAT, httpRequest.getMethod(), httpRequest.getUri().getPath());
        return RequestMap.keySet().stream()
                .filter(routeKey::equals)
                .map(RequestMap::get)
                .findAny().orElse(ErrorController::notFound)
                .apply(httpRequest, httpResponse);
    }
}
