package webserver.handler;

import db.DataBase;
import http.*;
import model.User;
import webserver.DynamicContentsFactory;


import java.io.IOException;
import java.util.Collection;

public class UserListHandler extends AbstractHandler {

    private static final UserListHandler INSTANCE = new UserListHandler();

    private UserListHandler() {
    }

    public static Handler getInstance() {
        return INSTANCE;
    }

    @Override
    public void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException {
        HttpHeaders httpHeaders = requestMessage.getHttpHeaders();

        if (httpHeaders.hasCookieValue("logined=true")) {
            Collection<User> allUsers = DataBase.findAll();

            byte[] body = DynamicContentsFactory.createHTML("/user/list", allUsers);

            responseMessage.responseWith(HttpStatus.OK, body, ResourceFormat.HTML);
            return;
        }
        responseMessage.responseResource(ResourceFormat.toRelativePath(Uri.from("/user/login.html")));
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {

    }
}
