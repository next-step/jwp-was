package controller;

import model.request.HttpRequest;
import model.response.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
