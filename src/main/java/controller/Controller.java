package controller;

import webserver.http.request.Request;

public interface Controller {
    String get(Request request);
}
