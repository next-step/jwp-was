package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import db.DataBase;
import model.User;
import webserver.http.Cookies;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserListController extends GetController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        Cookies cookies = request.getCookies();
        String cookie = cookies.getCookie("logined");

        if ("true".equals(cookie)) {
            Collection<User> users = DataBase.findAll();
            response.forward("user/list", users);
            return;
        }

        response.sendRedirect("/user/login.html");
    }
}
