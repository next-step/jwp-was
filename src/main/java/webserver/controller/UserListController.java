package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class UserListController extends GetController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/user/login.html");
            return;
        }

        Collection<User> users = DataBase.findAll();
        response.forward("user/list", users);
    }
}
