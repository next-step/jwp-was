package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

/**
 * Created by hspark on 2019-08-05.
 */
public interface Controller {
    void action(HttpRequest httpRequest, HttpResponse httpResponse);
    String getRequestUrl();
}
