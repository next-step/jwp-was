package webserver.http;

import webserver.controller.AbstractController;

import java.util.function.BiFunction;

public enum HttpMethod {
    GET(AbstractController::doGet),
    POST(AbstractController::doPost);

    private final BiFunction<AbstractController, HttpRequest, HttpResponse> action;

    HttpMethod(BiFunction<AbstractController, HttpRequest, HttpResponse> action) {
        this.action = action;
    }

    public BiFunction<AbstractController, HttpRequest, HttpResponse> getAction() {
        return action;
    }
}
