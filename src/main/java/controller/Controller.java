package controller;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

public abstract class Controller {
    protected final String path;

    protected Controller(final String path) {
        this.path = path;
    }

    public HttpResponse process(HttpRequest request) {
        if (request.getMethod() == HttpMethod.GET) {
            return get(request);
        }

        return post(request);
    }

    protected abstract HttpResponse get(HttpRequest request);
    protected abstract HttpResponse post(HttpRequest request);
}
