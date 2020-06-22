package handler;

import db.DataBase;
import dto.UserDto;
import http.common.HttpCookie;
import http.common.HttpHeader;
import http.common.HttpSession;
import http.request.QueryString;
import http.request.RequestMessage;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.ResponseMessage;
import model.User;
import webserver.StaticResourceLoader;


import java.io.IOException;

public class UserLoginHandler extends AbstractHandler {

    private static final UserLoginHandler INSTANCE = new UserLoginHandler();

    private UserLoginHandler() {
    }

    public static Handler getInstance() {
        return INSTANCE;
    }

    @Override
    public void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) {
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException {
        QueryString requestBody = requestMessage.readBody();
        User loginUser = DataBase.findUserById(requestBody.getFirstParameter("userId"));


        if (isLoginSuccess(requestBody, loginUser)) {
            HttpSession session = requestMessage.getSession();
            session.setAttribute("user", new UserDto(loginUser));

            setCookieTo(responseMessage, session);

            responseMessage.redirectTo("/index.html");
            return;
        }

        byte[] body = StaticResourceLoader.loadResource("/user/login_failed.html");

        responseMessage.forward(HttpStatus.OK, body, ContentType.HTML);
    }

    private boolean isLoginSuccess(QueryString requestBody, User loginUser) {
        return loginUser != null && loginUser.matchPassword(requestBody.getFirstParameter("password"));
    }

    private void setCookieTo(ResponseMessage responseMessage, HttpSession session) {
        HttpCookie cookie = HttpCookie.createEmpty();
        cookie.addCookieValueWithPath(HttpCookie.SESSION_ID, session.getId(), "/");
        responseMessage.setHeader(HttpHeader.SET_COOKIE, cookie.toJoinedString());
    }
}
