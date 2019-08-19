package webserver.controller;

import webserver.ResponseHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.OutputStream;

public class ForwardController implements Controller {
    @Override
    public void handle(OutputStream out, HttpRequest request) {
        HttpResponse response = new HttpResponse().ok(request.getRequestUri(), "text/html");
        ResponseHandler.response(out, response);
    }
}
