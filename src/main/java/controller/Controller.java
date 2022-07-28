package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public abstract class Controller {
    public static final String METHOD_NOT_ALLOWED = "허용되지 않은 메소드입니다.";

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new IllegalStateException(METHOD_NOT_ALLOWED);
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new IllegalStateException(METHOD_NOT_ALLOWED);
    }
}
