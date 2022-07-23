package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Controller {

    void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
