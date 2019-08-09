package webserver.controller;

import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.http.AbstractControllerCreator;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

public class UserLoginController extends AbstractControllerCreator {

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        User user = DataBase.findUserById(httpRequest
                .getParameter().get("userId"));


        String redirectUrl = (user != null) ?
                HttpConverter.BASIC_URL + "/index.html" :
                HttpConverter.BASIC_URL + "/user/login_failed.html";

        HttpResponse response = HttpResponse.reDirect(httpRequest, redirectUrl);

        if(user != null){
            response.setCookie("logined=true; Path=/");
        }else{
            response.setCookie("logined=false; Path=/user/login_failed.html");
        }
        return response;
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        return HttpResponse.setStatusResponse(HttpStatus.METHOD_NOT_ALLOWED);
    }

}
