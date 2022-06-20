package webserver.http.service;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryString;
import webserver.http.response.HttpResponse;

public class UserCreateService implements Service {
    @Override
    public boolean find(HttpRequest httpRequest) {
        return httpRequest.getPath().equals("/user/create");
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        QueryString queryString = httpRequest.getQueryString();
        String userId = queryString.get("userId");
        User user = new User(userId, queryString.get("password"), queryString.get("name"), queryString.get("email"));

        DataBase.addUser(user);
    }
}
