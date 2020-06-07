package controller;

import db.DataBase;
import dto.Users;
import http.request.QueryString;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewHandler;

import java.util.ArrayList;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public void createUser(QueryString queryString, ViewHandler viewHandler) {
        User user = new User(queryString);
        DataBase.addUser(user);
        logger.info("createUser :{}", user.toString());
        viewHandler.redirect("/index.html");
    }

    public boolean login(QueryString queryString, ViewHandler viewHandler) {
        User user = DataBase.findUserById(queryString.getUserId());
        boolean isLogin = user != null && user.equalsPassword(queryString.getPassword());
        logger.info("login : {}", isLogin);
        if (isLogin) {
            viewHandler.returnFile("/index.html");
        } else {
            viewHandler.returnFile("/user/login_failed.html");
        }
        return isLogin;
    }

    public Users userList() {
        return new Users(new ArrayList<>(DataBase.findAll()));
    }
}
