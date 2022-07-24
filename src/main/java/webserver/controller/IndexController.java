package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class IndexController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.forward("index");
    }
}
