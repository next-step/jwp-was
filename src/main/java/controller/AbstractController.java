package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpResponse httpResponse = HttpResponse.from(httpRequest);

        if (httpRequest.isGetMethod()) {
            doGet(httpRequest, httpResponse);
            return httpResponse;
        }

        if (httpRequest.isPostMethod()) {
            doPost(httpRequest, httpResponse);
            return httpResponse;
        }

        return httpResponse;
    }
}
