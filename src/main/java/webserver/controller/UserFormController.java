package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserFormController implements Controller {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.forward("user/form");
    }
}
