package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {
    void service(final HttpRequest request, final HttpResponse response) throws Exception;
}
