package webserver.http.request.controller;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public interface Controller {

    String postProcess(HttpRequest httpRequest, HttpResponse httpResponse);

    String getProcess(HttpRequest httpRequest, HttpResponse httpResponse);
}
