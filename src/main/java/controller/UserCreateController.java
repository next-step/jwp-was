package controller;

import db.DataBase;
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
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        final String userId = httpRequest.getParameter("userId");
        final String password = httpRequest.getParameter("password");
        final String name = httpRequest.getParameter("name");
        final String email = httpRequest.getParameter("email");
        DataBase.addUser(new User(userId, password, name, email));

        httpResponse.response302("/index.html");
    }
}
