package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import static utils.LoginUtil.LOGGED_IN;

public class LoginController extends AbstractController {

    public LoginController() {
        super("/user/login");
    }

    @Override
    public void get(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.setRedirect("/login.html");
    }

    @Override
    public void post(final HttpRequest request, final HttpResponse httpResponse) {
        String id = request.getParameter("userId");
        String password = request.getParameter("password");

        User user = DataBase.findUserById(id);

        if (user == null || !user.isPasswordValid(password)) {
            httpResponse.setRedirect("/user/login_failed.html");
            httpResponse.setCookie(LOGGED_IN, "false");

            return;
        }

        httpResponse.setRedirect("/index.html");
        httpResponse.setCookie(LOGGED_IN, "true");
    }
}
