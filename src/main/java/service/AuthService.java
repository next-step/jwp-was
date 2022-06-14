package service;

import db.DataBase;
import model.User;
import webserver.request.Request;
import webserver.response.HttpStatus;
import webserver.response.Response;
import webserver.response.ResponseHeader;

public class AuthService {
    private AuthService() {}

    public static Response login(Request request) {
        String userId = request.getBody("userId");
        String password = request.getBody("password");
        User user = DataBase.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            ResponseHeader responseHeader = new ResponseHeader(HttpStatus.FOUND)
                    .setLocation("/login_failed.html")
                    .setCookie("loggedIn=false");
            return new Response(responseHeader);
        }
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.FOUND)
                .setLocation("/index.html")
                .setCookie("loggedIn=true");
        return new Response(responseHeader);
    }
}
