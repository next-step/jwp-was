package http.controller;

import http.requests.HttpRequest;
import http.responses.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
