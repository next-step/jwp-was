package controller;

import model.*;
import utils.HandlebarsUtils;

import java.nio.charset.StandardCharsets;

public class UserListController implements Controller{

    public static final String USER_LOGIN_PATH = "/user/login.html";
    public static final String CONTENT_TYPE = "text/html";

    @Override
    public HttpResponse process(HttpRequest request) throws Exception {

        final Cookie cookie = request.getCookie();
        final String s = HandlebarsUtils.makeUserListTemplate();

        if (isLogin(cookie)) {
            return HttpResponse.success(new ResponseBody(s.getBytes()), CONTENT_TYPE);
        }
        return HttpResponse.redirect(USER_LOGIN_PATH);
    }

    private boolean isLogin(Cookie cookie) {
        if (cookie.isEmpty()) {
            return false;
        }
        return cookie.getName().equals("logined") && cookie.getValue().equals("true");
    }

}
