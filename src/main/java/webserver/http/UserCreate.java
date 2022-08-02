package webserver.http;

import db.DataBase;
import model.User;

import java.util.Map;

public class UserCreate {
    public UserCreate(String requestBody) {
        QueryStringParser queryStringParser = new QueryStringParser(requestBody);
        Map<String, String> params = queryStringParser.getQueryParameters();
        User user = new User(params.get("userId"), params.get("password"),params.get("name"),params.get("email"));
        DataBase.addUser(user);
    }
}
