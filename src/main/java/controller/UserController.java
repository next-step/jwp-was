package controller;

import db.DataBase;
import http.QueryString;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ViewHandler;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public void createUser(QueryString queryString, ViewHandler viewHandler) {
        User user = new User(queryString);
        DataBase.addUser(user);
        logger.info("createUser :{}", user.toString());
        viewHandler.redirect("/index.html");
    }

    public boolean login(QueryString queryString, ViewHandler viewHandler) {
        User user = DataBase.findUserById(queryString.getParam().get("userId"));
        boolean isLogin = user != null && user.getPassword().equals(queryString.getParam().get("password"));
        logger.info("login : {}", isLogin);
        if (isLogin) {
            viewHandler.returnUrl("/index.html");
        } else {
            viewHandler.returnUrl("/user/login_failed.html");
        }
        return isLogin;
    }
}
