package webserver.controller;

import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.http.AbstractControllerCreator;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class UserLoginController extends AbstractControllerCreator {

    @Override
    public void doPost(HttpRequest httpRequest) {
        User user = DataBase.findUserById(httpRequest
                .getParameter().get("userId"));
        if(user != null){
            HttpResponse.setRedirect(httpRequest,
                    HttpConverter.BASIC_URL + "/index.html");
            httpRequest.setCookie("logined=true; Path=/");
        }else{
            HttpResponse.setRedirect(httpRequest,
                    HttpConverter.BASIC_URL + "/user/login_failed.html");
            httpRequest.setCookie("logined=false; Path=/user/login_failed.html");
        }
    }

    @Override
    public void doGet(HttpRequest httpRequest) {

    }

}
