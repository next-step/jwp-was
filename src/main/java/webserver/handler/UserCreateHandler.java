package webserver.handler;

import db.DataBase;
import http.RequestMessage;
import http.ResponseMessage;
import model.User;

public class UserCreateHandler implements Handler {

    private static final UserCreateHandler INSTANCE = new UserCreateHandler();

    private UserCreateHandler() {
    }

    public static Handler getInstance() {
        return INSTANCE;
    }

    @Override
    public void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) {
        User user = new User(requestMessage.getQueryString());
        byte[] body = user.toString().getBytes();

        responseMessage.response200Header(body.length);
        responseMessage.responseBody(body);
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {
        User user = new User(requestMessage.getBody());
        DataBase.addUser(user);

        responseMessage.redirectTo("/index.html");
    }
}
