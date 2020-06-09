package controller;

import db.DataBase;
import dto.Users;
import http.request.QueryString;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public void createUser(QueryString queryString, HttpResponse httpResponse) {
        User user = new User(queryString);
        DataBase.addUser(user);
        logger.info("createUser :{}", user.toString());
        httpResponse.sendRedirect("/index.html");
    }

    public boolean login(QueryString queryString, HttpResponse httpResponse) {
        User user = DataBase.findUserById(queryString.getParameter("userId"));
        boolean isLogin = user != null && user.equalsPassword(queryString.getParameter("password"));
        logger.info("login : {}", isLogin);
        httpResponse.addHeader("logined=", Boolean.toString(isLogin));
        if (isLogin) {
            httpResponse.forward("/index.html");
        } else {
            httpResponse.forward("/user/login_failed.html");
        }
        return isLogin;
    }

    public Users userList() {
        return new Users(new ArrayList<>(DataBase.findAll()));
    }
}
