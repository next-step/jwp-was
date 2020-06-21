package webserver.controller;

import webserver.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Controller {
    ModelAndView service(HttpRequest request, HttpResponse response);
}
