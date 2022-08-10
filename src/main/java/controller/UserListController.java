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
        if (cookie.isLogin()) {
            return HttpResponse.success(new ResponseBody(s.getBytes()), CONTENT_TYPE);
        }
        return HttpResponse.redirect(USER_LOGIN_PATH);
    }

}
