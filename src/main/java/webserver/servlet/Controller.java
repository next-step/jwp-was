package webserver.servlet;

import webserver.enums.HttpMethod;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface Controller {

    default void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getMethod() == HttpMethod.POST) {
            doPost(httpRequest, httpResponse);
            return;
        }

        if (httpRequest.getMethod() == HttpMethod.GET) {
            doGet(httpRequest, httpResponse);
            return;
        }

        httpResponse.methodNotAllowed();
    }

    default void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new UnsupportedOperationException();
    }

    default void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new UnsupportedOperationException();
    }

}
