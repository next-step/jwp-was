package webserver.http.service;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Service {

    boolean find(HttpRequest httpRequest);

    void doService(HttpRequest httpRequest, HttpResponse httpResponse);
}
