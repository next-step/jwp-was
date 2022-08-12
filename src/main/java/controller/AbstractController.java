package controller;

import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller{

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if (request.getMethod() == HttpMethod.GET) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    public void doPost(HttpRequest request, HttpResponse response) throws IOException {};
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {};
}
