package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        String name = httpRequest.getParameter("name");
        String email = httpRequest.getParameter("email");
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
        httpResponse.sendRedirect("/index.html");
    }
}
