package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {
    void process(HttpRequest httpRequest, HttpResponse httpResponse);
    String getPath();
}
