package webserver.controller;

import webserver.http.request.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ControllerMapper implements ControllerProvider {

    private static final Controller NOT_FOUND = (ignore, response) -> response.notFound();

    private final Map<String, Controller> pathOfController = new HashMap<>();

    public void register(final String path,
                         final Controller controller) {
        pathOfController.put(path, controller);
    }

    public Controller provide(final Request request) {
        return pathOfController.entrySet()
                .stream()
                .filter(matchPath(request))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(NOT_FOUND);
    }

    private Predicate<Map.Entry<String, Controller>> matchPath(final Request request) {
        return entry -> entry.getKey().matches(request.getPath());
    }
}
