package http.controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
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

    @Override
    protected void doPOST(HttpRequest request, HttpResponse response) {
        final String userId = request.getParameter("userId");
        final String password = request.getParameter("password");

        final User user = DataBase.findUserById(userId);

        if (user != null && user.login(password)) {
            setCurrentSessionAttribute(request, LOGIN_COOKIE_KEY, LOGIN_COOKIE_STATUS_TRUE);
            response.redirect(LOGIN_SUCCESS_REDIRECT_URL);
            return;
        }

        setCurrentSessionAttribute(request, LOGIN_COOKIE_KEY, LOGIN_COOKIE_STATUS_FALSE);
        response.redirect(LOGIN_FAIL_REDIRECT_URL);
    }

    private void setCurrentSessionAttribute(HttpRequest request, String key, String value) {
        HttpSession session = getCurrentSession(request);
        session.setAttribute(key, value);
    }
}
