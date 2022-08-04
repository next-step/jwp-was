package controller;

import request.HttpRequest;
import response.HttpResponse;

public interface Controller {
    public void service(HttpRequest request, HttpResponse response) throws Exception;
}
