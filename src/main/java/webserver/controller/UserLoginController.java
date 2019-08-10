package webserver.controller;

import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.http.AbstractControllerStructor;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserLoginController extends AbstractControllerStructor {

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

}
