package controller;

import model.*;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.response.ResponseBody;
import utils.HandlebarsUtils;

public class UserListController extends AbstractController{

    public static final String USER_LOGIN_PATH = "/user/login.html";
    public static final String CONTENT_TYPE = "text/html";
    public static final String COOKIE_NAME_LOGINED = "logined";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {

        final Cookie cookie = request.getCookie(COOKIE_NAME_LOGINED);
        final String s = HandlebarsUtils.makeUserListTemplate();

        if (isLogin(cookie)) {
            response.forward(new ResponseBody(s.getBytes()), CONTENT_TYPE);
            return;
        }
        response.redirect(USER_LOGIN_PATH);
    }

    private boolean isLogin(Cookie cookie) {
        if (cookie == null || cookie.isEmpty()) {
            return false;
        }
        return cookie.getName().equals(COOKIE_NAME_LOGINED) && cookie.getValue().equals("true");
    }

}
