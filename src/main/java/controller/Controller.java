package controller;

import view.ModelAndView;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Controller {
    ModelAndView get(HttpRequest request, HttpResponse response);
}
