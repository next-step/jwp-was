package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.model.request.HttpRequest;
import webserver.http.model.response.HttpResponse;

import java.io.IOException;

public class CreateUserController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        DataBase.addUser(new User(httpRequest.getRequestBody().getRequestBodyMap()));
        httpResponse.moveNotStaticResourcePage(httpResponse, "/index.html");
    }
}
