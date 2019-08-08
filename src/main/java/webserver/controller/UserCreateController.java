package webserver.controller;

import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.domain.HttpResponseEntity;
import webserver.http.AbstractControllerCreator;
import webserver.http.HttpRequest;
import webserver.http.HttpStatus;

public class UserCreateController extends AbstractControllerCreator {

    @Override
    public HttpResponseEntity doPost(HttpRequest httpRequest) {
        User user = new User(httpRequest.getParameter().get("userId"),
                httpRequest.getParameter().get("password"),
                httpRequest.getParameter().get("name"),
                httpRequest.getParameter().get("email"));
        DataBase.addUser(user);

        HttpResponseEntity entity = new HttpResponseEntity(httpRequest.getHttpHeader(),
                HttpStatus.REDIRECT.getHttpStatusCode(),
                httpRequest.getVersion());

        entity.setResultBody(HttpStatus.REDIRECT.getHttpStatusMessage());
        entity.setRedirectUrl(HttpConverter.BASIC_URL + "/index.html");

        return entity;
    }

    @Override
    public HttpResponseEntity doGet(HttpRequest httpRequest) {
        return HttpResponseEntity.setStatusResponse(httpRequest,
                HttpStatus.METHOD_NOT_ALLOWED);
    }
}
