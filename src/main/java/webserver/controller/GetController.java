package webserver.controller;

import webserver.exception.NotFoundMethod;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class GetController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) {
        if (notGet(request)) {
            throw new NotFoundMethod("해당 메서드는 지원하지않습니다.");
        }

        return doGet(request);
    }

    private boolean notGet(HttpRequest request) {
        return !request.isGet();
    }

    public abstract HttpResponse doGet(HttpRequest request);
}
