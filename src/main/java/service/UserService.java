package service;

import db.DataBase;
import http.QueryStrings;
import model.User;

public class UserService {

    public static void join(String requestBody) {
        QueryStrings queryStrings = new QueryStrings(requestBody);

        User user = new User(queryStrings.getValue("userId"), queryStrings.getValue("password"), queryStrings.getValue("name"), queryStrings.getValue("email"));

        DataBase.addUser(user);
    }
}
