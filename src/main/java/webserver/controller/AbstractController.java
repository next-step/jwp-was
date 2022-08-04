package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public abstract class AbstractController implements Controller {
    public static final String METHOD_NOT_PROCESSABLE = "처리 가능한 메소드가 아닙니다.";
    public static final String METHOD_NOT_ALLOWED = "허용되지 않은 메소드입니다.";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        if (httpRequest.isGet()) {
            return doGet(httpRequest);
        }
        if (httpRequest.isPost()) {
            return doPost(httpRequest);
        }
        throw new IllegalStateException(METHOD_NOT_PROCESSABLE);
    }

    public HttpResponse doGet(HttpRequest httpRequest) {
        throw new IllegalStateException(METHOD_NOT_ALLOWED);
    }

    public HttpResponse doPost(HttpRequest httpRequest) {
        throw new IllegalStateException(METHOD_NOT_ALLOWED);
    }
}
