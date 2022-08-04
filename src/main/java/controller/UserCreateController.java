package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

public class UserCreateController extends AbstractController {
    private static final String INDEX = "/index.html";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User user = new User(request.getBodyParameter("userId")
                , request.getBodyParameter("password")
                , request.getBodyParameter("name")
                , request.getBodyParameter("email"));

        DataBase.addUser(user);
        response.sendRedirect(INDEX);
    }
}
