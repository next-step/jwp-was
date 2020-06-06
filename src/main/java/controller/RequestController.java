package controller;

import db.DataBase;
import http.QueryString;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    public void createUser(QueryString queryString) {
        User user = new User(queryString);
        DataBase.addUser(user);
        logger.info("createUser :{}", user.toString());
    }

    public boolean login(QueryString queryString) {
        User user = DataBase.findUserById(queryString.getParam().get("userId"));
        return user != null && user.getPassword().equals(queryString.getParam().get("password"));
    }
}
