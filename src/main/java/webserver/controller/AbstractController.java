package webserver.controller;

import java.util.Map;
import java.util.function.Function;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.controller.exception.UnsupportedHttpMethodException;

public abstract class AbstractController implements Controller {

    private final Map<HttpMethod, Function<HttpRequest, HttpResponse>> functions = Map.of(
        HttpMethod.GET, this::doGet,
        HttpMethod.POST, this::doPost
    );

    @Override
    public HttpResponse service(HttpRequest request) {
        var function = functions.get(request.getMethod());
        if (function == null) {
            throw new UnsupportedHttpMethodException();
        }

        return function.apply(request);
    }

    public HttpResponse doGet(HttpRequest request) {
        throw new UnsupportedHttpMethodException();
    }

    public HttpResponse doPost(HttpRequest request) {
        throw new UnsupportedHttpMethodException();
    }
}
