package controller;

import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if (HttpMethod.POST.equals(request.getMethod())) {
            doPost(request, response);
        } else if (HttpMethod.GET.equals(request.getMethod())) {
            doGet(request, response);
        }
    }

    abstract void doPost(HttpRequest request, HttpResponse response) throws Exception;

    abstract void doGet(HttpRequest request, HttpResponse response) throws Exception;

}
