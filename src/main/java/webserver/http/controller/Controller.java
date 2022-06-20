package webserver.http.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;

public interface Controller {
    void service(final HttpRequest request, final HttpResponse response) throws IOException;
}
