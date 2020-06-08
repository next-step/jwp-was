package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.LoginUtil;

public class UserListController extends AbstractController {

    public UserListController() {
        super("/user/list");
    }

    @Override
    protected void get(final HttpRequest request, final HttpResponse httpResponse) {
        if (!LoginUtil.isLoggedIn(request)) {
            httpResponse.setRedirect("/user/login.html");
            return;
        }

        httpResponse.forward("user/profile");
        httpResponse.addModel("users", DataBase.findAll());
    }
}
