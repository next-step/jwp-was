package webserver.http.controller;

import webserver.exception.UnSupportedHttpMethodException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isGet()) {
            doGet(httpRequest, httpResponse);
            return;
        }

        if (httpRequest.isPost()) {
            doPost(httpRequest, httpResponse);
            return;
        }

        throw new UnSupportedHttpMethodException();
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new UnSupportedHttpMethodException();
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new UnSupportedHttpMethodException();
    }
}
