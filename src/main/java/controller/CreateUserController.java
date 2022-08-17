package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class CreateUserController extends AbstractController {
    @Override
    void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        DataBase.addUser(user);

        response.sendRedirect("/index.html");
    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws Exception {
    }
}
