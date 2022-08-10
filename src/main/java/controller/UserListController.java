package controller;

import model.*;
import utils.HandlebarsUtils;

public class UserListController implements Controller{

    public static final String USER_LOGIN_PATH = "/user/login.html";

    @Override
    public HttpResponse process(HttpRequest request) throws Exception {

        final Cookie cookie = request.getCookie();

        if (cookie.isLogin()) {
            return HttpResponse.success(HandlebarsUtils.makeUserListTemplate().getBytes());
        }
        return HttpResponse.redirect(USER_LOGIN_PATH, request.getHeader());
    }

}
