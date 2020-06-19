package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface Controller {
    ModelAndView service(HttpRequest request, HttpResponse response);
}
