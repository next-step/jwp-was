package controller;

import http.HttpRequest;
import http.HttpResponse;

public interface Controller {
    HttpResponse service(HttpRequest httpRequest);
}
