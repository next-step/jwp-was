package webserver.response.post;

import db.DataBase;

public class PostUserCreateResponse {
    public void response(String requestBody) {
        DataBase.addUser(UserParser.parseToUser(requestBody));
    }
}
