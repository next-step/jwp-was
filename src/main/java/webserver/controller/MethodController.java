package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.request.requestline.Method;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class MethodController implements Controller {
    @Override
    public HttpResponse process(HttpRequest httpRequest) throws URISyntaxException, IOException {
        if (httpRequest.isMethodEqual(Method.GET)) {
            return processGet(httpRequest);
        }
        if (httpRequest.isMethodEqual(Method.POST)) {
            return processPost(httpRequest);
        }
        return HttpResponse.notFound();
    }

    abstract HttpResponse processGet(HttpRequest httpRequest) throws URISyntaxException, IOException;
    abstract HttpResponse processPost(HttpRequest httpRequest) throws URISyntaxException, IOException;
}
