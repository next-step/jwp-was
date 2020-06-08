package controller;

import http.HttpRequest;

public interface Controller {
    void execute(HttpRequest httpRequest);
}
