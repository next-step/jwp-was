package webserver.http.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isGet()) {
            doGet(httpRequest, httpResponse);
        }

        if (httpRequest.isPost()) {
            doPost(httpRequest, httpResponse);
        }

        // TODO Throw
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new UnsupportedOperationException("지원하지 않은 기능입니다.");
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new UnsupportedOperationException("지원하지 않은 기능입니다.");
    }
}
