package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.OutputStream;

public interface Controller {
    public HttpResponse handle(HttpRequest request);
}
