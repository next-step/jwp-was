package service;

import db.DataBase;
import model.User;
import webserver.request.Request;
import webserver.response.HttpStatus;
import webserver.response.Response;
import webserver.response.ResponseHeader;

public class CreateUserService {
    private CreateUserService() {}

    public static Response createUser(Request request) {
        User user = new User(request.getBody("userId"),
                request.getBody("password"),
                request.getBody("name"),
                request.getBody("email")
        );
        DataBase.addUser(user);

        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.FOUND)
                .setLocation("/index.html");
        return new Response(responseHeader);
    }
}
