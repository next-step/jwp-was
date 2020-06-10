package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

public class LoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.getBody("userId");
        String password = httpRequest.getBody("password");

        User user = DataBase.findUserById(userId);
        if (canUserLoggedIn(user, password)) {
            loginSuccess(httpResponse);
            return;
        }

        loginFailed(httpResponse);
    }

    private boolean canUserLoggedIn(User user, String password) {
        return user != null && user.isSamePassword(password);
    }

    private void loginSuccess(HttpResponse httpResponse) {
        httpResponse.setLoginCookie(true, "/");
        httpResponse.redirect("/index.html");
    }

    private void loginFailed(HttpResponse httpResponse) {
        httpResponse.setLoginCookie(false);
        httpResponse.redirect("/user/login_failed.html");
    }
}
