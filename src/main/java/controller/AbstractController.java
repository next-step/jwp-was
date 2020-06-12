package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isGetMethod()) {
            doGet(httpRequest, httpResponse);
            return;
        }

        if (httpRequest.isPostMethod()) {
            doPost(httpRequest, httpResponse);
        }
    }
}
