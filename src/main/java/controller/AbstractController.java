package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.request.HttpMethod;

abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if (HttpMethod.POST.equals(request.getMethod())) {
            doPost(request, response);
        } else {
            doGet(request, response);
        }
    }

    abstract void doPost(HttpRequest request, HttpResponse response) throws Exception;

    abstract void doGet(HttpRequest request, HttpResponse response) throws Exception;
}
