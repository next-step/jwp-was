package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class PathController extends AbstractController {
    @Override
    public void doGet(final HttpRequest request, final HttpResponse response) {
        response.templateForward();
    }
}
