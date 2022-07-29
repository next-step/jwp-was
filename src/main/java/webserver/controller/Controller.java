package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface Controller {
    HttpResponse execute(HttpRequest httpRequest) throws Exception;
}
