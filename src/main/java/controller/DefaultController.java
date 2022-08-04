package controller;

import request.HttpRequest;
import response.HttpResponse;

public class DefaultController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        if(request.getPath().equals("/")) {
            response.forward("/index.html");
            return;
        }

        response.forward(request.getPath());
    }
}
