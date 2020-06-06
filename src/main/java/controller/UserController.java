package controller;

import db.DataBase;
import http.QueryStrings;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    public static void join(QueryStrings queryStrings) {
        User user = new User(queryStrings.getValue("userId"), queryStrings.getValue("password"), queryStrings.getValue("name"), queryStrings.getValue("email"));

        DataBase.addUser(user);
    }

    public static boolean isLoginSuccess(QueryStrings queryStrings) {
        String userId = queryStrings.getValue("userId");
        String password = queryStrings.getValue("password");

        User savedUser = DataBase.findUserById(userId);
        return savedUser.matchPassword(password);
    }

    public static List<User> findAll() {
        return new ArrayList<>(DataBase.findAll());
    }
}
