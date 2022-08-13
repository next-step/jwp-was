package user.controller;

import webserver.http.model.request.HttpMethod;
import webserver.http.model.request.HttpRequest;
import webserver.http.model.response.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        if (HttpMethod.isPost(request.getMethod())) {
            doPost(request, response);
            return;
        }
        doGet(request, response);
    }

    public abstract void doPost(HttpRequest request, HttpResponse response) throws IOException;

    public abstract void doGet(HttpRequest request, HttpResponse response) throws IOException;
}
