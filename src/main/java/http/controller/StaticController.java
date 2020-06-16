package http.controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class StaticController extends AbstractController{

    @Override
    public void doGet(final HttpRequest request, final HttpResponse response) throws Exception {
        setContentType(response, request.getPath());
        response.staticFileForward();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        response.notExistApi();
    }

    private void setContentType(final HttpResponse response, final String path) {
        if (path.endsWith(".css")) {
            response.addHeader("Content-Type", "text/css");
        } else if (path.endsWith(".js")) {
            response.addHeader("Content-Type", "application/javascript");
        }
    }
}
