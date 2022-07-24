package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

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

    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {}

    protected void doPost(HttpRequest request, HttpResponse response) {}
}
