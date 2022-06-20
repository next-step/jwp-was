package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpStatus;
import webserver.QueryString;
import webserver.Request;
import webserver.Response;

public class LoginController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public Response serving(Request request) {
        User user = getUserFromRequest(request);

        User userById = DataBase.findUserById(user.getUserId());

        if (userById == null || !userById.getPassword().equals(user.getPassword())) {
            return new Response(HttpStatus.BAD_REQUEST, "text/html;charset=utf-8", "/user/login_failed.html", "logined=false; Path=/");
        }

        return new Response(HttpStatus.FOUND, "text/html;charset=utf-8", "/index.html", "logined=true; Path=/");
    }

    private User getUserFromRequest(Request request) {
        QueryString queryString = request.getRequestLine().toQueryString();
        return new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );
    }
}
