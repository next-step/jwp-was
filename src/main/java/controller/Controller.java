package controller;

import constant.HttpMethod;
import request.HttpRequest;
import response.HttpResponse;

public interface Controller {
    public void service(HttpRequest request, HttpResponse response) throws Exception;

}
