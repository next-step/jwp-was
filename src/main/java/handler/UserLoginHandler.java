package handler;

import db.DataBase;
import http.common.HttpHeader;
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

        if(loginUser != null && loginUser.matchPassword(requestBody.getFirstParameter("password"))) {
            responseMessage.setHeader(HttpHeader.SET_COOKIE, "logined=true; Path=/");
            responseMessage.redirectTo("/index.html");
        } else {
            byte[] body = StaticResourceLoader.loadResource("/user/login_failed.html");

            responseMessage.setHeader(HttpHeader.SET_COOKIE, "logined=false");
            responseMessage.responseWith(HttpStatus.OK, body, ContentType.HTML);
        }

    }
}
