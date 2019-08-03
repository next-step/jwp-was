package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Optional;

public class ErrorController {
    public static Optional notFound(HttpRequest httpRequest, HttpResponse httpResponse) {
        return Optional.of("404 Not Found");
    }
}
