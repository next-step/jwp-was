package controller;

import constant.HttpMethod;
import request.HttpRequest;
import response.HttpResponse;

public abstract class AbstractController implements Controller {
    private static final String NOT_FOUND = "기능이 정의 되지 않았습니다.";

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if(request.isGetRequest()) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        throw new IllegalArgumentException(NOT_FOUND);
    }

    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        throw new IllegalArgumentException(NOT_FOUND);
    }

}
