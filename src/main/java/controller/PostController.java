package controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class PostController implements Controller {

    abstract HttpResponse doPost(HttpRequest httpRequest);

    @Override
    public final HttpResponse doService(HttpRequest httpRequest) {
        return doPost(httpRequest);
    }
}
