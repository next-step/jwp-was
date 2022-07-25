package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class GetController implements Controller {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        doGet(request, response);
    }

    protected abstract void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
