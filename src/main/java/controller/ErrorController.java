package controller;

import webserver.http.HttpRequest;

import java.util.Optional;

public class ErrorController {
    public static Optional notFound(HttpRequest httpRequest) {
        return Optional.of("404 Not Found");
    }
}
