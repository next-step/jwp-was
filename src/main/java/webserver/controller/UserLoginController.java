package webserver.controller;

import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.domain.HttpResponseEntity;
import webserver.http.AbstractControllerCreator;
import webserver.http.HttpRequest;
import webserver.http.HttpStatus;

public class UserLoginController extends AbstractControllerCreator {

    @Override
    public HttpResponseEntity doPost(HttpRequest httpRequest) {
        User user = DataBase.findUserById(httpRequest
                .getParameter().get("userId"));

        HttpResponseEntity entity = new HttpResponseEntity(httpRequest.getHttpHeader(),
                HttpStatus.REDIRECT.getHttpStatusCode(),
                httpRequest.getVersion());

        if(user != null){
            entity.setRedirectUrl(HttpConverter.BASIC_URL + "/index.html");
            entity.setCookie("logined=true; Path=/");

        }else{
            entity.setRedirectUrl(HttpConverter.BASIC_URL + "/user/login_failed.html");
            entity.setCookie("logined=false; Path=/user/login_failed.html");
        }

        return entity;
    }

    @Override
    public HttpResponseEntity doGet(HttpRequest httpRequest) {
        return HttpResponseEntity.setStatusResponse(httpRequest,
                HttpStatus.METHOD_NOT_ALLOWED);
    }

}
