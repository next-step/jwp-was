package webserver.controller;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        HttpMethod httpMethod = request.getMethod();

        if (httpMethod.isPost()) {
            doPost(request, response);
        }
        if (httpMethod.isGet()) {
            doGet(request, response);
        }
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
    }
}
