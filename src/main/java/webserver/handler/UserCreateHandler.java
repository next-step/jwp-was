package webserver.handler;

import db.DataBase;
import http.ContentType;
import http.HttpStatus;
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

        responseMessage.responseWith(HttpStatus.OK, body, ContentType.PLAIN);
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {
        User user = new User(requestMessage.readBody());
        DataBase.addUser(user);

        responseMessage.redirectTo("/index.html");
    }
}
