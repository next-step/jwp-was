package webserver.controller;

import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.http.AbstractControllerStructor;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class UserLoginController extends AbstractControllerStructor {

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        User user = DataBase.findUserById(httpRequest
                .getParameter().get("userId"));


        String redirectUrl = (user != null) ?
                HttpConverter.BASIC_URL + "/index.html" :
                HttpConverter.BASIC_URL + "/user/login_failed.html";

        HttpResponse response = HttpResponse.reDirect(httpRequest, redirectUrl);

        HttpSession httpSession = httpRequest.getSession();

        if(user != null){
            response.addCookie("logined=true; Path=/");
            httpSession.setAttribute("logined", true);
        }else{
            response.addCookie("logined=false; Path=/user/login_failed.html");
            httpSession.setAttribute("logined", false);
        }

        return response;
    }

}
