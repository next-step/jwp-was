package controller;

import db.DataBase;
import model.User;
import webserver.QueryString;
import webserver.Request;
import webserver.Response;

public class CreatUserController implements Controller {
    public Response serving(Request request) {
        DataBase.addUser(getUser(request));
        return new Response("/index.html");
    }

    private User getUser(Request request) {
        QueryString queryString = request.getRequestLine().toQueryString();
        return new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );
    }
}
