package http.controller;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
