package webserver.http.request.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public interface Controller {
    ModelAndView process(HttpRequest httpRequest, HttpResponse httpResponse);
    boolean isAllowAll();
}
