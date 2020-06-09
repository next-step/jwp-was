package http.controller;

import db.DataBase;
import http.*;

import http.enums.ContentType;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController extends PathController{

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    public void doPost(HttpRequest request, HttpResponse response) {
        log.info("login controller post method ===========");

        response.addHeader("Content-Type", ContentType.html.getMimeType());

        QueryString requestBodyString = new QueryString(request.getRequestBody());
        User loginUser = new User(requestBodyString.getParameter("userId"), requestBodyString.getParameter("password"));
        User findUser = DataBase.findUserById(requestBodyString.getParameter("userId"));

        if(findUser != null && findUser.equals(loginUser)) {
            response.addHeader("Set-Cookie","logined=true; Path=/");
            response.sendRedirect("/index.html");
            return;
        }

        response.addHeader("Set-Cookie", "logined=false");
        response.sendRedirect("/user/login_failed.html");

    }
}
