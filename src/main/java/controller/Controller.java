package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws IOException;
}
