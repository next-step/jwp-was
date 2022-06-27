package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.*;

public class LoginController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public Response service(Request request) {
        User user = getUserFromRequest(request);

        User userById = DataBase.findUserById(user.getUserId());

        if (userById == null || !userById.checkPassword(user.getPassword())) {
            return new Response(HttpStatus.BAD_REQUEST, MediaType.TEXT_HTML_UTF8, "/user/login_failed.html", "logined=false; Path=/");
        }

        return new Response(HttpStatus.FOUND, MediaType.TEXT_HTML_UTF8, "/index.html", "logined=true; Path=/");
    }

    private User getUserFromRequest(Request request) {
        QueryString queryString = request.getRequestLine().toQueryString();

        if (request.getRequestLine().getMethod() == HttpMethod.POST) {
            queryString = QueryString.parse(request.getRequestBody());
        }

        return new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );
    }
}
