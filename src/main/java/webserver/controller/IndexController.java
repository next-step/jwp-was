package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class IndexController implements Controller {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        HttpMethod method = request.getMethod();
        if (method.isGet()) {
            response.forward("index");
        }
    }
}
