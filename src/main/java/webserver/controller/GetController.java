package webserver.controller;

import exception.HttpMethodNotSupportedException;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class GetController implements Controller {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        HttpMethod method = request.getMethod();
        if (!method.isGet()) {
            throw new HttpMethodNotSupportedException(method);
        }
        doGet(request, response);
    }

    protected abstract void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException;
}
