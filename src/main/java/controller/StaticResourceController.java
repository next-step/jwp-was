package controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class StaticResourceController implements Controller {

    @Override
    public void process(final HttpRequest request, final HttpResponse response) {
        response.forward(request.getPathValue());
    }
}
