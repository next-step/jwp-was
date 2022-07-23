package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.HttpRequest;
import webserver.HttpResponse;

public interface Controller {

    void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
