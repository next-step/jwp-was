package webserver.controller;

import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class PostController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isPost()) {
            doPost(request, response);
        }
    }

    public abstract void doPost(HttpRequest request, HttpResponse response);
}
