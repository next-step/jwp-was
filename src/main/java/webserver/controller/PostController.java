package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class PostController implements Controller {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        doPost(request, response);
    }

    protected abstract void doPost(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
