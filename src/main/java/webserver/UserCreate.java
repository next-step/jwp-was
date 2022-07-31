package webserver;

import db.DataBase;
import model.User;

import java.util.Map;

public class UserCreate {
    public UserCreate(String requestBody) {
        QueryStringParser QSParser = new QueryStringParser(requestBody);
        Map<String, String> params = QSParser.getQueryParameters();
        User user = new User(params.get("userId"), params.get("password"),params.get("name"),params.get("email"));
        DataBase.addUser(user);
    }
}
