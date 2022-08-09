package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class GetController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGet()) {
            doGet(request, response);
        }
    }

    public abstract void doGet(HttpRequest request, HttpResponse response);
}
