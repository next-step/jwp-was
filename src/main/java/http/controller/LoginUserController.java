package http.controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class LoginUserController extends AbstractController {

    private static final String LOGIN_SUCCESS_REDIRECT_URL = "/index.html";
    private static final String LOGIN_FAIL_REDIRECT_URL = "/user/login_failed.html";
    private static final String LOGIN_COOKIE_KEY = "logined";
    private static final String LOGIN_COOKIE_STATUS_TRUE = "true";
    private static final String LOGIN_COOKIE_STATUS_FALSE = "false";
    private static final String LOGIN_COOKIE_PATH = "/";

    @Override
    protected void doGET(HttpRequest request, HttpResponse response) {
    }

    @Override
    protected void doPOST(HttpRequest request, HttpResponse response) {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");

        final User user = DataBase.findUserById(userId);
        if (user != null && user.login(password)) {
            response.addCookie(LOGIN_COOKIE_KEY, LOGIN_COOKIE_STATUS_TRUE);
            response.addCookiePath(LOGIN_COOKIE_PATH);
            response.redirect(LOGIN_SUCCESS_REDIRECT_URL);
            return;
        }

        response.addCookie(LOGIN_COOKIE_KEY, LOGIN_COOKIE_STATUS_FALSE);
        response.addCookiePath(LOGIN_COOKIE_PATH);
        response.redirect(LOGIN_FAIL_REDIRECT_URL);
    }
}
