package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class IndexController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.forward("index");
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
