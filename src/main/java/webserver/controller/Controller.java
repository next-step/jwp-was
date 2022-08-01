package webserver.controller;

import webserver.http.HttpSession;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface Controller {

    boolean isMatch(HttpRequest request);

    HttpResponse execute(HttpRequest request, HttpSession session) throws Exception;
}
