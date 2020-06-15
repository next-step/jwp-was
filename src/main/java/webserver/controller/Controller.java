package webserver.controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface Controller {
    void service(HttpRequest req, HttpResponse resp);
}
