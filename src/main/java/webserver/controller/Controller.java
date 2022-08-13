package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {

    HttpResponse service(HttpRequest request) throws IOException, URISyntaxException;
}
