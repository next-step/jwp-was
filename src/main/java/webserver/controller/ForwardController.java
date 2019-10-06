package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseResolver;

public class ForwardController implements Controller {
    @Override
    public HttpResponse handle(HttpRequest request) {
        return HttpResponseResolver.forward("text/html", request.getRequestUri());
    }
}
