package controller;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller{

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {

        if(request.getMethod().isPost()) {
            doPost(request, response);
        } else {
            doGet(request, response);
        }
    }

    public void doPost(HttpRequest request, HttpResponse response) {

    }

    public void doGet(HttpRequest request, HttpResponse response) {

    }
}
