package http.controller;

import http.HttpMethod;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (HttpMethod.GET.name().equals(httpRequest.method())) {
            doGet(httpRequest, httpResponse);
        }
        else if(HttpMethod.POST.name().equals(httpRequest.method())) {
            doPost(httpRequest, httpResponse);
        }
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
