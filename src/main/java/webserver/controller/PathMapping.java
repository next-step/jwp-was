package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;

public enum RequestApiPath {
    CREATE("/user/create", (request, response) -> {
        new CreateUserController().service(request, response);
    }),
    LIST("/user/list", (request, response) -> {
        new ListUserController().service(request, response);
    }),
    LOGIN("/user/login", (request, response) -> {
        new LoginController().service(request, response);
    });

    String path;
    BiConsumer<HttpRequest, HttpResponse> consumer;

    RequestApiPath(String path, BiConsumer<HttpRequest, HttpResponse> consumer) {
        this.path = path;
        this.consumer = consumer;
    }

    public static Optional<RequestApiPath> findMappingUrl(HttpRequest request) {
        return Arrays.stream(RequestApiPath.values())
                .filter(value -> value.path.startsWith(request.getRequestPath()))
                .findAny();
    }

    public void findPath(HttpRequest request, HttpResponse response) {
        this.consumer.accept(request, response);
    }
}
