package user.controller;

import exception.UnsupportedMethodException;
import model.User;
import user.service.CreateUserService;
import webserver.http.model.request.HttpRequest;
import webserver.http.model.response.HttpResponse;

import java.io.IOException;

public class CreateUserController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        throw new UnsupportedMethodException();
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        CreateUserService.create(new User(httpRequest.getRequestBody().getRequestBodyMap()));
        httpResponse.movePage("/index.html");
    }
}
