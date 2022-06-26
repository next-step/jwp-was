package webserver.adapter.in.controller;

import webserver.adapter.in.HttpRequest;
import webserver.adapter.out.web.HttpResponse;
import webserver.domain.http.HttpMethod;

import java.io.IOException;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        HttpMethod httpMethod = httpRequest.getHttpMethod();

        if (httpMethod == HttpMethod.GET) {
            doGet(httpRequest, httpResponse);
        }

        doPost(httpRequest, httpResponse);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        throw new RuntimeException("Not implemented");
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        throw new RuntimeException("Not implemented");
    }
}
