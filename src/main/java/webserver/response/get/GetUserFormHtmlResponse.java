package webserver.response.get;

import db.DataBase;
import model.User;
import webserver.request.header.RequestHeader;

public class GetUserFormHtmlResponse {
    public void response(RequestHeader requestHeader) {
        User user = new User(
                requestHeader.requestParams("userId"),
                requestHeader.requestParams("password"),
                requestHeader.requestParams("name"),
                requestHeader.requestParams("email")
        );

        DataBase.addUser(user);
    }
}
