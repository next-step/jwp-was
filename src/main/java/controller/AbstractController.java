package controller;

import http.HttpRequest;
import http.method.HttpMethod;

import static http.method.HttpMethod.GET;
import static http.method.HttpMethod.POST;

public abstract class AbstractController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest) {
        HttpMethod httpMethod = httpRequest.getHttpMethod();

        if (httpMethod == GET) {
            doGet(httpRequest);
        }

        if (httpMethod == POST) {
            doPost(httpRequest);
        }
    }

    abstract void doGet(HttpRequest httpRequest);

    abstract void doPost(HttpRequest httpRequest);
}
