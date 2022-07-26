package webserver.controller;

import exception.HttpMethodNotSupportedException;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class PostController implements Controller {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        HttpMethod method = request.getMethod();
        if (!method.isPost()) {
            throw new HttpMethodNotSupportedException(method);
        }
        doPost(request, response);
    }

    protected abstract void doPost(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
