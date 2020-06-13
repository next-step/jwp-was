package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isGet()) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    public void doPost(HttpRequest request, HttpResponse response) {
    }

    public void doGet(HttpRequest request, HttpResponse response) {
    }
}
