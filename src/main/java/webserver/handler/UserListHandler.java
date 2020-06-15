package webserver.handler;

import db.DataBase;
import http.*;
import model.User;
import webserver.DynamicContentsFactory;


import java.io.IOException;
import java.util.Collection;

public class UserListHandler implements Handler {

    private static final UserListHandler INSTANCE = new UserListHandler();

    private UserListHandler() {
    }

    public static Handler getInstance() {
        return INSTANCE;
    }

    @Override
    public void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException {
        Header header = requestMessage.getHeader();

        if (header.hasCookieValue("logined=true")) {
            Collection<User> allUsers = DataBase.findAll();

            byte[] body = DynamicContentsFactory.createHTML("/user/list", allUsers);

            responseMessage.response200Header(body.length);
            responseMessage.responseBody(body);
            return;
        }
        responseMessage.responseResource(ContentType.toRelativePath(Uri.from("/user/login.html")));
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {

    }
}
