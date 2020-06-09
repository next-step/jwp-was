package http.controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class LoginUserController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(LoginUserController.class);

    private static final String LOGIN_SUCCESS_REDIRECT_URL = "/index.html";
    private static final String LOGIN_FAIL_REDIRECT_URL = "/user/login_failed.html";
    private static final String USER_ID_FIELD ="userId";
    private static final String PASSWORD_FIELD ="password";
    private static final String SET_COOKIE_HEADER_KEY = "Set-Cookie";
    private static final String LOGIN_SUCCESS_COOKIE = "logined=true; Path=/";
    private static final String LOGIN_FAIL_COOKIE = "logined=fail; Path=/";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParameter(USER_ID_FIELD));
        if (isLoginSuccess(user, request)) {
            logger.debug("User : {}", user);
            response.addHeaders(SET_COOKIE_HEADER_KEY, LOGIN_SUCCESS_COOKIE);
            response.redirect(LOGIN_SUCCESS_REDIRECT_URL);
            return;
        }
        response.addHeaders(SET_COOKIE_HEADER_KEY, LOGIN_FAIL_COOKIE);
        response.redirect(LOGIN_FAIL_REDIRECT_URL);
    }

    private boolean isUserByUserId(User user) {
        return user != null;
    }

    private boolean isLoginSuccess(User user, HttpRequest request) {
        return isUserByUserId(user) && user.isPasswordCorrect(request.getParameter(PASSWORD_FIELD));
    }
}
