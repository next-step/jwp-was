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

        doGet(httpRequest, httpResponse);
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse);

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse);

}
