package controller;

import constant.HttpHeader;
import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

public class LoginController extends AbstractController {
    private static final String SUCCESS_VIEW = "/index.html";
    private static final String FAIL_VIEW = "/user/login_failed.html";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String COOKIE_FORMAT = "logined=%s; Path=/";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User user = DataBase.findUserById(request.getBodyParameter("userId"));
        String view = FAIL_VIEW;
        String logined = FALSE;

        if (isLogin(user, request.getBodyParameter("password"))) {
            view = SUCCESS_VIEW;
            logined = TRUE;
        }

        response.addHeader(HttpHeader.SET_COOKIE.getValue(), String.format(COOKIE_FORMAT, logined));
        response.sendRedirect(view);
    }

    private boolean isLogin(User user, String passowrd) {
        return user != null && user.getPassword().equals(passowrd);
    }

}
