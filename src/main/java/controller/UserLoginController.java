package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Map;

public class UserLoginController extends Controller {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        final Map<String, String> payloads = httpRequest.getPayloads();

        final User user = DataBase.findUserById(payloads.get("userId"));
        if (isLogin(payloads, user)) {
            setLoginCookie(httpResponse, false);
            httpResponse.redirect("/user/login_failed.html");
            return;
        }

        setLoginCookie(httpResponse, true);
        httpResponse.redirect("/index.html");
    }

    private boolean isLogin(Map<String, String> payloads, User user) {
        return user == null || !user.isLogin(payloads.get("password"));
    }

    private void setLoginCookie(HttpResponse httpResponse, boolean login) {
        httpResponse.addHeader("Set-Cookie", "logined=" + login + "; Path=/");
    }
}
