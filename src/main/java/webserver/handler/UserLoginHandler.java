package webserver.handler;

import db.DataBase;
import http.QueryString;
import http.RequestMessage;
import http.ResponseMessage;
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

        if (loginUser == null) {
            responseMessage.setHeader("Set-Cookie", "logined=false");
            responseMessage.redirectTo("/user/login_failed.html");
        } else {
            responseMessage.setHeader("Set-Cookie", "logined=true");
            responseMessage.redirectTo("/index.html");
        }
    }
}
