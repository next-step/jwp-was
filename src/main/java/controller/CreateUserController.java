package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class CreateUserController implements Controller {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
