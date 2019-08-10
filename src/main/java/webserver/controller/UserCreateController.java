package webserver.controller;

import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.http.AbstractControllerStructor;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserCreateController extends AbstractControllerStructor {

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        User user = new User(httpRequest.getParameter().get("userId"),
                httpRequest.getParameter().get("password"),
                httpRequest.getParameter().get("name"),
                httpRequest.getParameter().get("email"));
        DataBase.addUser(user);

        return HttpResponse.reDirect(httpRequest,
                HttpConverter.BASIC_URL + "/index.html");
    }
}
