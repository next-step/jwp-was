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
        QueryString requestBody = requestMessage.getBody();
        User loginUser = DataBase.findUserById(requestBody.getFirstParameter("userId"));

        if(loginUser != null && loginUser.matchPassword(requestBody.getFirstParameter("password"))) {
            responseMessage.setHeader("Set-Cookie", "logined=true; Path=/");
            responseMessage.redirectTo("/index.html");
        } else {
            responseMessage.setHeader("Set-Cookie", "logined=false");
            responseMessage.responseResource(ContentType.toRelativePath(Uri.from("/user/login_failed.html")));
        }

    }
}
