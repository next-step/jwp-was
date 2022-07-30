package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Collection;

public class UserListController extends Controller {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        final Boolean logined = Boolean.valueOf(httpRequest.getCookie("logined"));
        if (!logined) {
            httpResponse.redirect("/index.html");
        }

        final Collection<User> users = DataBase.findAll();

        httpResponse.forward("user/list", users);
    }
}
