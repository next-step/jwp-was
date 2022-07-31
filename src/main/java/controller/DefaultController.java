package controller;

import request.HttpRequest;
import response.HttpResponse;

public class DefaultController extends Controller{

    @Override
    public HttpResponse doGet(HttpRequest request) throws Exception {
        return HttpResponse.forward(request.getPath());
    }
}
