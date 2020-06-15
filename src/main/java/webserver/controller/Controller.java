package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface Controller {
    void service(HttpRequest req, HttpResponse resp);
}
