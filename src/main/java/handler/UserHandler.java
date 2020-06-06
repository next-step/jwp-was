package handler;

import db.DataBase;
import http.request.HttpRequest;
import http.HttpResponse;
import http.response.EmptyHttpResponse;
import model.User;

public class UserHandler {

    public HttpResponse create(HttpRequest httpRequest) {
        String userId = httpRequest.getQueryParam("userId");
        String password = httpRequest.getQueryParam("password");

        String name = httpRequest.getQueryParam("name");
        String email = httpRequest.getQueryParam("email");

        User user = new User(userId, password, name, email);

        DataBase.addUser(user);

        return new EmptyHttpResponse();
    }
}
