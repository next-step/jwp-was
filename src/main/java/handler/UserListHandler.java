package handler;

import db.DataBase;
import http.common.HttpHeaders;
import http.request.RequestMessage;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.ResponseMessage;
import model.User;
import webserver.DynamicContentsFactory;
import webserver.StaticResourceLoader;


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

        if(httpHeaders.isLogin()) {
            Collection<User> allUsers = DataBase.findAll();

            byte[] body = DynamicContentsFactory.createHTML("/user/list", allUsers);

            responseMessage.responseWith(HttpStatus.OK, body, ContentType.HTML);
            return;
        }

        byte[] body = StaticResourceLoader.loadResource("/user/login.html");
        responseMessage.responseWith(HttpStatus.OK, body, ContentType.HTML);
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {

    }
}
