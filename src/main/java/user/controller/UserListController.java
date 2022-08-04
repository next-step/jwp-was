package user.controller;

import controller.Controller;
import db.DataBase;
import user.model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Collection;

public class UserListController extends Controller {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        final boolean logined = httpRequest.getCookie("logined", Boolean.class);
        if (!logined) {
            HttpResponse.sendRedirect("/index.html");
        }

        final Collection<User> users = DataBase.findAll();

        return HttpResponse.forward("user/list", users);
    }
}
