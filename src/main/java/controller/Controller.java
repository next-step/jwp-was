package controller;

import constant.HttpMethod;
import request.HttpRequest;
import response.HttpResponse;

public abstract class Controller {
    private static final String NOT_FOUND = "기능이 정의 되지 않았습니다.";

    public HttpResponse service(HttpRequest request) throws Exception {
        if(request.getHttpMethod().equals(HttpMethod.GET)) {
            return doGet(request);
        }
        return doPost(request);
    }

    public HttpResponse doGet(HttpRequest request) throws Exception {
        throw new IllegalArgumentException(NOT_FOUND);
    }

    public HttpResponse doPost(HttpRequest request) throws Exception {
        throw new IllegalArgumentException(NOT_FOUND);
    }

}
