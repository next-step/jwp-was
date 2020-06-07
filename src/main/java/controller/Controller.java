package controller;

import http.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;

public interface Controller {
    void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
