package controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {
    @Override
    public HttpResponse service(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpRequest.isPost()) {
            return doPost(httpRequest);
        }
        return doGet(httpRequest);
    }

    public HttpResponse doPost(HttpRequest httpRequest) {
        return HttpResponse.notfound();
    }

    public HttpResponse doGet(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return HttpResponse.notfound();
    }
}
