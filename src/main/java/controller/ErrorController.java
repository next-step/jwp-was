package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class ErrorController extends AbstractController {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        error(request, response, response.getStatus());
    }
}
