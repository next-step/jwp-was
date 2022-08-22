package controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.request.Cookie;
import webserver.http.request.HttpSession;

import java.util.Objects;

public class LoginController extends AbstractController {
    @Override
    void doPost(HttpRequest request, HttpResponse response) throws Exception {
        Cookie cookie = request.getCookie();
        User loginUser = DataBase.findUserById(request.getParameter("userId"));
        boolean isLogin = isLogin(loginUser, request.getParameter("password"));
        cookie.set("logined", String.valueOf(isLogin));

        HttpSession session = request.getSession(cookie.getSessionId());
        session.setAttribute("user", loginUser);

        response.addHeader(Cookie.RESPONSE_COOKIE_HEADER, cookie.getResponseCookie());
        response.sendRedirect(isLogin ? "/index.html" : "/user/login_failed.html");
    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws Exception {
        response.response404();
    }

    private boolean isLogin(User user, String password) {
        return !Objects.isNull(user) && user.checkPassword(password);
    }
}
