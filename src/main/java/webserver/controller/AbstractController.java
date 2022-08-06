package webserver.controller;

import webserver.constant.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller{
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        HttpMethod httpMethod = httpRequest.getMethod();
        if (httpMethod == HttpMethod.POST) {
            doPost(httpRequest, httpResponse);
        }
        if (httpMethod == HttpMethod.GET) {
            doGet(httpRequest, httpResponse);
        }
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
