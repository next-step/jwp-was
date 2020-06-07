package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
