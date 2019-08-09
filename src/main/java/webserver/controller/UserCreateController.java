package webserver.controller;

import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.http.AbstractControllerCreator;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

public class UserCreateController extends AbstractControllerCreator {

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

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        return HttpResponse.setStatusResponse(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
