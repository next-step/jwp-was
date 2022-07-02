package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RequestController {
    HttpResponse doGet(HttpRequest httpRequest) throws IOException, URISyntaxException;

    HttpResponse doPost(HttpRequest httpRequest) throws IOException, URISyntaxException;
}
