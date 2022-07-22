package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface Controller {

    boolean isMatch(HttpRequest request);

    HttpResponse execute(HttpRequest request) throws Exception;
}
