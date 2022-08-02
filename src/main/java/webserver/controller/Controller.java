package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
