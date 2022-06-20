package webserver.http.service;

import db.DataBase;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.QueryString;
import webserver.http.response.HttpResponse;

public class UserCreateGetService extends GetService {
    @Override
    protected boolean pathMatch(HttpRequest httpRequest) {
        return httpRequest.getPath().equals("/user/create");
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        QueryString queryString = httpRequest.getQueryString();
        User user = new User(queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email"));

        DataBase.addUser(user);

        httpResponse.ok(null);
    }
}
