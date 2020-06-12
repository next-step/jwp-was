package controller;

import http.request.HttpRequest;
import http.request.method.HttpMethod;
import http.response.HttpResponse;

import static http.request.method.HttpMethod.GET;
import static http.request.method.HttpMethod.POST;

public abstract class AbstractController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpMethod httpMethod = httpRequest.getHttpMethod();

        if (httpMethod == GET) {
            doGet(httpRequest, httpResponse);
        }

        if (httpMethod == POST) {
            doPost(httpRequest, httpResponse);
        }
    }
}
