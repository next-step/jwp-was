package webserver.controller;

import webserver.ResponseHandler;
import webserver.http.mapping.RequestMapping;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseResolver;

import java.io.OutputStream;

public class ForwardController implements Controller {
    @Override
    public HttpResponse handle(HttpRequest request) {
        return HttpResponseResolver.forward("text/html", request.getRequestUri());
    }
}
