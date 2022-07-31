package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface Controller {
    Boolean canExecute(HttpRequest httpRequest);

    HttpResponse execute(HttpRequest httpRequest) throws Exception;
}
