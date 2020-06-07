package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import utils.LoginUtil;

public class UserListController extends AbstractController {

    public UserListController() {
        super("/user/list");
    }

    @Override
    protected void get(final HttpRequest request, final HttpResponse httpResponse) {
        if (!LoginUtil.isLoggedIn(request)) {
            httpResponse.sendRedirect("/user/login.html");
            return;
        }

        httpResponse.forward("user/profile");
        httpResponse.addModel("users", DataBase.findAll());
    }
}
