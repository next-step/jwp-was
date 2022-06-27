package webserver.http.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Controller {

    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
