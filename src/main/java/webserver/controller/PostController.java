package webserver.controller;

import webserver.exception.NotFoundMethod;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class PostController implements Controller {

    @Override
    public HttpResponse service(HttpRequest request) {
        if (!request.isPost()) {
            throw new NotFoundMethod("해당 메서드는 지원하지않습니다.");
        }

        return doPost(request);
    }

    public abstract HttpResponse doPost(HttpRequest request);
}
