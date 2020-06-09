package controller;

import http.request.HttpRequest;

public interface Controller {
    void execute(HttpRequest httpRequest);
}
