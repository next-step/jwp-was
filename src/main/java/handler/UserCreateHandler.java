package handler;

import db.DataBase;
import http.response.ContentType;
import http.response.HttpStatus;
import http.request.RequestMessage;
import http.response.ResponseMessage;
import model.User;

public class UserCreateHandler extends AbstractHandler {

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
