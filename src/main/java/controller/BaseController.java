package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class BaseController implements Controller {
    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {

    }

    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) {

    }

    public HttpResponse doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        return httpResponse;
    }
}
