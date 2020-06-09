package http.controller;

import http.HttpRequest;
import http.HttpResponse;

/**
 * Created By kjs4395 on 2020-06-08
 */
public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
