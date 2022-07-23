package webserver.response.get;

import db.DataBase;
import model.User;
import webserver.header.request.requestline.RequestLine;

public class GetUserFormHtmlResponse {
    public void response(RequestLine requestLine) {
        User user = new User(
                requestLine.requestParams().get("userId"),
                requestLine.requestParams().get("password"),
                requestLine.requestParams().get("name"),
                requestLine.requestParams().get("email")
        );

        DataBase.addUser(user);
    }
}
