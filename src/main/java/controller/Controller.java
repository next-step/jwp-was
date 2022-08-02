package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public abstract class Controller {
    public static final String METHOD_NOT_ALLOWED = "허용되지 않은 메소드입니다.";

    public HttpResponse doGet(HttpRequest httpRequest) {
        throw new IllegalStateException(METHOD_NOT_ALLOWED);
    }

    public HttpResponse doPost(HttpRequest httpRequest) {
        throw new IllegalStateException(METHOD_NOT_ALLOWED);
    }
}
