package http.controller;

import db.DataBase;
import http.*;
import http.enums.HttpResponseCode;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginController extends PathController{

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    public void doPost(HttpRequest request, HttpResponse response) {
        log.info("login controller post method ===========");

        Header headerInfo = new Header();
        headerInfo.addKeyAndValue("Content-Type","text/html;charset=utf-8");

        QueryString requestBodyString = new QueryString(request.getRequestBody());
        User loginUser = new User(requestBodyString.getParameter("userId"), requestBodyString.getParameter("password"));
        User findUser = DataBase.findUserById(requestBodyString.getParameter("userId"));

        if(loginUser.equals(findUser)) {
            response.addHeader("Set-Cookie","logined=true; Path=/");
            response.sendRedirect("/index.html");
            return;
        }

        response.addHeader("Set-Cookie", "logined=false");
        response.sendRedirect("/user/login_failed.html");

    }
}
