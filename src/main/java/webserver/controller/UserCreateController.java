package webserver.controller;

import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.http.AbstractControllerCreator;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserCreateController extends AbstractControllerCreator {

    @Override
    public void doPost(HttpRequest httpRequest) {
        User user = new User(httpRequest.getParameter().get("userId"),
                httpRequest.getParameter().get("password"),
                httpRequest.getParameter().get("name"),
                httpRequest.getParameter().get("email"));
        DataBase.addUser(user);

        HttpResponse.setRedirect(httpRequest,
                HttpConverter.BASIC_URL + "/index.html");
    }

    @Override
    public void doGet(HttpRequest httpRequest) {

    }
}
