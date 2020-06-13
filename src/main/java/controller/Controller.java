package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Controller {

    HttpResponse execute(HttpRequest httpRequest);

    default void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    default void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
