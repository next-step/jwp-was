package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Controller {
    HttpResponse service(HttpRequest httpRequest);
}
