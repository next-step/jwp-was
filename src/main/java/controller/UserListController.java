package controller;

import db.DataBase;
import model.*;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.HttpSession;
import model.response.ResponseBody;
import utils.HandlebarsUtils;

public class UserListController extends AbstractController{

    public static final String USER_LOGIN_PATH = "/user/login.html";
    public static final String CONTENT_TYPE = "text/html";
    public static final String COOKIE_NAME_LOGINED = "logined";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {

        final Cookie cookie = request.getCookie("sessionId");
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
        final HttpSession session = DataBase.findSession(cookie.getValue());

        final Object logined = session.getAttribute("logined");

        return (logined != null && logined.equals("true"));
    }

}
