package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;

public enum PathMapping {
    CREATE("/user/create", (request, response) -> {
        new CreateUserController().service(request, response);
    }),
    LIST("/user/list", (request, response) -> {
        new ListUserController().service(request, response);
    }),
    LOGIN("/user/login", (request, response) -> {
        new LoginController().service(request, response);
    }),
    NONE("none", ((request, response) -> {
        new NotFoundController().service(request, response);
    }));

    String path;
    BiConsumer<HttpRequest, HttpResponse> consumer;

    PathMapping(String path, BiConsumer<HttpRequest, HttpResponse> consumer) {
        this.path = path;
        this.consumer = consumer;
    }

    public static PathMapping findMappingUrl(HttpRequest request) {
        return Arrays.stream(PathMapping.values())
                .filter(value -> value.path.startsWith(request.getRequestPath()))
                .findAny()
                .orElse(PathMapping.NONE);
    }

    public void mappingController(HttpRequest request, HttpResponse response) {
        this.consumer.accept(request, response);
    }

    public static boolean isExist(String requestPath) {
        return Arrays.stream(PathMapping.values())
                .anyMatch(value -> value.path.equals(requestPath));
    }
}
