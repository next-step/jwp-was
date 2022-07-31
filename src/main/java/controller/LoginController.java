package controller;

import constant.HttpContentType;
import constant.HttpHeader;
import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

public class LoginController extends Controller {
    private static final String SUCCESS_VIEW = "/index.html";
    private static final String FAIL_VIEW = "/user/login_failed.html";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    @Override
    public HttpResponse doPost(HttpRequest request) throws Exception {
        User user = DataBase.findUserById(request.getBodyParameter("userId"));
        String view = SUCCESS_VIEW;
        String logined = TRUE;

        if(isNotLogin(user, request.getBodyParameter("password"))) {
            view = FAIL_VIEW;
            logined = FALSE;
        }

        HttpResponse response = HttpResponse.sendRedirect(view);
        response.addHeader(HttpHeader.SET_COOKIE.getValue(), String.format("logined=%s; Path=/", logined));

        return response;
    }

    private boolean isNotLogin(User user, String passowrd) {
        return user == null || !user.getPassword().equals(passowrd);
    }

}
