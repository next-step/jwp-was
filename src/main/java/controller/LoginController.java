package controller;

import constant.HttpCookie;
import constant.HttpHeader;
import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import response.ResponseCookie;
import session.SessionManager;

public class LoginController extends AbstractController {

    private static final String SUCCESS_VIEW = "/index.html";
    private static final String FAIL_VIEW = "/user/login_failed.html";

    private static final String FALSE = "false";

    private static final String PASSWORD_FIELD = "password";

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User user = DataBase.findUserById(request.getBodyParameter("userId"));
        String view = FAIL_VIEW;
        response.addHeader(HttpHeader.SET_COOKIE.getValue(),
            ResponseCookie.of(HttpCookie.LOGIN.getValue(), FALSE).toString());

        if (user.isLogin(request.getBodyParameter(PASSWORD_FIELD))) {
            view = SUCCESS_VIEW;
            response.addHeader(HttpHeader.SET_COOKIE.getValue(),
                ResponseCookie.of(HttpCookie.JSESSIONID.getValue(), SessionManager.createSession().getId()).toString());
        }
        response.sendRedirect(view);
    }

}
