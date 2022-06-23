package service;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public interface Controller {

    HttpResponse doService(HttpRequest httpRequest);

    boolean canServe(HttpRequest httpRequest);
}
