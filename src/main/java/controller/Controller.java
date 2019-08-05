package controller;

import webserver.http.request.HttpRequest;

public interface Controller {
    String get(HttpRequest request);
}
