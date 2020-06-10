package controller.user;

import controller.AbstractController;
import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class UserListController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isLoginUser()) {
            httpResponse.setModel("users", DataBase.findAll());
            httpResponse.renderTemplate("/user/list");
            return;
        }

        httpResponse.redirect("/user/login.html");
    }
}
