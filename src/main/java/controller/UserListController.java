package controller;

import model.*;
import utils.HandlebarsUtils;

public class UserListController implements Controller{

    public static final String USER_LOGIN_PATH = "/user/login.html";
    public static final String CONTENT_TYPE = "text/html";

    @Override
    public HttpResponse process(HttpRequest request) throws Exception {

        final Cookie cookie = request.getCookie();

        if (cookie.isLogin()) {
            return HttpResponse.success(new ResponseBody(HandlebarsUtils.makeUserListTemplate().getBytes()), CONTENT_TYPE);
        }
        return HttpResponse.redirect(USER_LOGIN_PATH);
    }

}
