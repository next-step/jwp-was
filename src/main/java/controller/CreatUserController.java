package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpStatus;
import webserver.QueryString;
import webserver.Request;
import webserver.Response;

public class CreatUserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(CreatUserController.class);

    public Response service(Request request) {
        DataBase.addUser(getUserFromRequest(request));
        logger.debug("findAll: {}", DataBase.findAll());
        return new Response(HttpStatus.FOUND, "text/html;charset=utf-8", "/index.html", null);
    }

    private User getUserFromRequest(Request request) {
        QueryString queryString = QueryString.parse(request.getRequestBody());
        return new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );
    }
}
