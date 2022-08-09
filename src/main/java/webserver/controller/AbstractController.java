package webserver.controller;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public abstract class AbstractController implements Controller {
    public static final String METHOD_NOT_ALLOWED = "허용되지 않은 메소드입니다.";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        final HttpMethod httpMethod = httpRequest.getHttpMethod();
        return httpMethod.getAction().apply(this, httpRequest);
    }

    public HttpResponse doGet(HttpRequest httpRequest) {
        throw new IllegalStateException(METHOD_NOT_ALLOWED);
    }

    public HttpResponse doPost(HttpRequest httpRequest) {
        throw new IllegalStateException(METHOD_NOT_ALLOWED);
    }
}
