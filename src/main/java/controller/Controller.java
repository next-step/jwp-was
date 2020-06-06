package controller;

import http.HttpRequest;
import http.HttpResponse;

public interface Controller {
    void process(HttpRequest httpRequest, HttpResponse httpResponse);
}
