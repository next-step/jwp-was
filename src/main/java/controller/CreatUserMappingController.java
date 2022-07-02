package controller;

import db.DataBase;
import http.*;
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
    public HttpResponse doPost(HttpRequest httpRequest) {
        DataBase.addUser(getUserFromRequest(httpRequest));
        logger.debug("findAll: {}", DataBase.findAll());
        return new HttpResponse(HttpStatus.FOUND, MediaType.TEXT_HTML_UTF8, "/index.html", null);
    }

    private User getUserFromRequest(HttpRequest httpRequest) {
        QueryString queryString = QueryString.parse(httpRequest.getRequestBody());
        return new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );
    }
}
