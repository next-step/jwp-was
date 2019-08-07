package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

/**
 * control request with httpRequest & httpResponse
 */
public interface Controller {

    void service(HttpRequest httpRequest, HttpResponse httpResponse);

}
