package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserFormController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.forward("user/form");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {

    }
}
