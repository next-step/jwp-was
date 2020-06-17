package webserver.handler;

import db.DataBase;
import http.*;
import model.User;

public class UserLoginHandler implements Handler {

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
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {
        QueryString requestBody = requestMessage.readBody();
        User loginUser = DataBase.findUserById(requestBody.getFirstParameter("userId"));

        if(loginUser != null && loginUser.matchPassword(requestBody.getFirstParameter("password"))) {
            responseMessage.setHeader(HttpHeader.SET_COOKIE, "logined=true; Path=/");
            responseMessage.redirectTo("/index.html");
        } else {
            responseMessage.setHeader(HttpHeader.SET_COOKIE, "logined=false");
            responseMessage.responseResource(ContentType.toRelativePath(Uri.from("/user/login_failed.html")));
        }

    }
}
