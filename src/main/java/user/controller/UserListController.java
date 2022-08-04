package user.controller;

import db.DataBase;
import user.model.User;
import webserver.controller.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Collection;

public class UserListController extends AbstractController {
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
