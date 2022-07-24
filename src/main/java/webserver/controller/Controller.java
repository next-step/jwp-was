package webserver.controller;

import http.request.HttpRequest;

public interface Controller {
    void run(HttpRequest requestLine);
}
