package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        HttpMethod method = request.getMethod();
        if (method.isGet()) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    protected abstract void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;

    protected abstract void doPost(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
