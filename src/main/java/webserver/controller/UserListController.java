package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public class UserListController extends GetController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String cookie = request.getHeaders().getCookie();
        if (cookie != null && cookie.contains("logined=true")) {
            Collection<User> users = DataBase.findAll();
            response.forward("user/list", users);
            return;
        }
        response.sendRedirect("/user/login.html");
    }
}
