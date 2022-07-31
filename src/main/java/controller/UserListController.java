package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Collection;

public class UserListController extends Controller {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        final boolean logined = httpRequest.getCookie("logined", Boolean.class);
        if (!logined) {
            httpResponse.redirect("/index.html");
        }

        final Collection<User> users = DataBase.findAll();

        httpResponse.forward("user/list", users);
    }
}
