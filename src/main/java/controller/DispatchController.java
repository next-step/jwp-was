package controller;

import model.HttpRequest;
import model.HttpResponse;

import java.util.Set;

public class DispatchController {
    private static Set<Controller> controllers;

    static {
        controllers = Set.of(
                new CreateUserController()
        );
    }

    public HttpResponse handleRequest(HttpRequest request) {
        HttpResponse httpResponse = controllers.stream()
                .filter(controller -> controller.matchHttpMethodAndPath(request))
                .findAny()
                .map(controller -> controller.execute(request))
                .orElseGet(() -> HttpResponse.notFound());
        return httpResponse;

    }
}
