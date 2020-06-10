package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class PathController extends AbstractController {

    public void doGet(HttpRequest request, HttpResponse response) {
        if (request.isStylesheet()) {
            response.viewStyleSheet(request.getPath());
            return;
        }
        response.forward(request.getPath());
    }

}
