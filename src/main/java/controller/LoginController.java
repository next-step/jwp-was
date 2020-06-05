package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class LoginController extends Controller {

    public LoginController() {
        super("/user/login");
    }

    @Override
    public HttpResponse get(final HttpRequest request) {
        return HttpResponse.redirect("/login.html");
    }

    @Override
    public HttpResponse post(final HttpRequest request) {
        String id = request.getBodyParameter("userId");
        String password = request.getBodyParameter("password");

        User user = DataBase.findUserById(id);

        if (user == null || !user.isPasswordValid(password)) {
            HttpResponse httpResponse = HttpResponse.redirect("/user/login_failed.html");
            httpResponse.setCookie("logined", "false");

            return httpResponse;
        }

        HttpResponse httpResponse = HttpResponse.redirect("/index.html");
        httpResponse.setCookie("logined", "true");

        return httpResponse;
    }
}
