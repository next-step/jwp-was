package controller;

import db.DataBase;
import webserver.http.*;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreatUserMappingController extends RequestMappingControllerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CreatUserMappingController.class);

    @Override
    public boolean checkUrl(String url) {
        return "/user/create".equals(url);
    }

    @Override
    public HttpResponse doPost(Request request) {
        DataBase.addUser(getUserFromRequest(request));
        logger.debug("findAll: {}", DataBase.findAll());
        return new HttpResponse(HttpStatus.FOUND, MediaType.TEXT_HTML_UTF8, "/index.html", null);
    }

    private User getUserFromRequest(Request request) {
        return new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
    }
}
