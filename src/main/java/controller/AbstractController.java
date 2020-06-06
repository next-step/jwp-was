package controller;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public abstract class AbstractController {
    protected final String path;

    protected AbstractController(final String path) {
        this.path = path;
    }

    public HttpResponse process(final HttpRequest request) throws IOException {
        if (request.getMethod() == HttpMethod.GET) {
            return get(request);
        }

        return post(request);
    }

    protected abstract HttpResponse get(final HttpRequest request) throws IOException;
    protected abstract HttpResponse post(final HttpRequest request);

    public String getPath() {
        return path;
    }
}
