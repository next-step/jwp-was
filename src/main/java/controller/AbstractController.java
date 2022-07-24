package controller;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isSameMethod(HttpMethod.POST)) {
            doPost(request, response);
            return;
        }
        doGet(request, response);
    }

    abstract void doGet(HttpRequest request, HttpResponse response);

    abstract void doPost(HttpRequest request, HttpResponse response);

}
