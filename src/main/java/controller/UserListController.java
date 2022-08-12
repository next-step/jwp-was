package controller;

import model.*;
import utils.HandlebarsUtils;

import java.nio.charset.StandardCharsets;

public class UserListController implements Controller{

    public static final String USER_LOGIN_PATH = "/user/login.html";
    public static final String CONTENT_TYPE = "text/html";
    public static final String COOKIE_NAME_LOGINED = "logined";

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {

        final Cookie cookie = request.getCookie(COOKIE_NAME_LOGINED);
        final String s = HandlebarsUtils.makeUserListTemplate();

        if (isLogin(cookie)) {
            response.success(new ResponseBody(s.getBytes()), CONTENT_TYPE);
            response.writeResponse();
            return;
        }
        response.redirect(USER_LOGIN_PATH);
        response.writeResponse();

    }

    private boolean isLogin(Cookie cookie) {
        if (cookie == null || cookie.isEmpty()) {
            return false;
        }
        return cookie.getName().equals(COOKIE_NAME_LOGINED) && cookie.getValue().equals("true");
    }

}
