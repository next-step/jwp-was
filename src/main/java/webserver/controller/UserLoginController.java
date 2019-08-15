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

        HttpSession newSession = response.getHttpSession();

        if(user != null){
            newSession.setAttribute("logined", true);
            newSession.setAttribute("Path", "/");
        }else{
            newSession.setAttribute("logined", false);
            newSession.setAttribute("Path", "/user/login_failed.html");
        }

        response.addSession(newSession);
        return response;
    }

}
