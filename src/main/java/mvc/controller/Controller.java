package mvc.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws Exception;
}
