package controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class DefaultController extends Controller {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getPath());
    }
}
