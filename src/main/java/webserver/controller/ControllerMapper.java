package webserver.controller;

import webserver.http.request.Request;

import java.util.HashMap;
import java.util.Map;

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
                .filter(entry -> request.matchPath(entry.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(NOT_FOUND);
    }
}
