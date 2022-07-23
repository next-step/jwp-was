package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserLoginController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.forward("user/login");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {

    }
}
