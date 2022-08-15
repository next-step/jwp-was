package controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    HttpResponse service(HttpRequest httpRequest) throws IOException, URISyntaxException;
}
