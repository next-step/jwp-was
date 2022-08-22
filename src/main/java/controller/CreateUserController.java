package controller;

import db.DataBase;
import exception.InvalidHttpMethodException;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class CreateUserController extends AbstractController {
    @Override
    void doPost(HttpRequest request, HttpResponse response) {
        User user = new User(
                request.getBodyParameter("userId"),
                request.getBodyParameter("password"),
                request.getBodyParameter("name"),
                request.getBodyParameter("email")
        );

        DataBase.addUser(user);
        response.sendRedirect("/index.html");

    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws InvalidHttpMethodException {
        throw new InvalidHttpMethodException();
    }
}
