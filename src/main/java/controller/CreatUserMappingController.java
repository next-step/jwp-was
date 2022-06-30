package controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.*;

public class CreatUserMappingController extends RequestMappingControllerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CreatUserMappingController.class);

    @Override
    public boolean checkUrl(String url) {
        return "/user/create".equals(url);
    }

    @Override
    public Response doPost(Request request) {
        DataBase.addUser(getUserFromRequest(request));
        logger.debug("findAll: {}", DataBase.findAll());
        return new Response(HttpStatus.FOUND, MediaType.TEXT_HTML_UTF8, "/index.html", null);
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
