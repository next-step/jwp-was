package controller;

import db.DataBase;
import http.request.FormData;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import webserver.controller.AbstractController;

public class UserCreateController extends AbstractController {

    private UserCreateController() {}

    private static class Singleton {
        private static final UserCreateController instance = new UserCreateController();
    }

    public static UserCreateController getInstance() {
        return Singleton.instance;
    }

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        FormData formData = new FormData(httpRequest.getBody());
        String userId = formData.getValue("userId");
        String password = formData.getValue("password");
        String name = formData.getValue("name");
        String email = formData.getValue("email");

        DataBase.addUser(new User(userId, password, name, email));

        httpResponse.response302("/index.html");
    }
}
