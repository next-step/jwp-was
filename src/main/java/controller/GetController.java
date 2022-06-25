package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class GetController implements Controller {

    abstract HttpResponse doGet(HttpRequest httpRequest);

    @Override
    public final HttpResponse doService(HttpRequest httpRequest) {
        return doGet(httpRequest);
    }
}
