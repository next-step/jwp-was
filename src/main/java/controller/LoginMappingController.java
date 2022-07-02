package controller;

import db.DataBase;
import webserver.http.*;
import model.User;

public class LoginMappingController extends RequestMappingControllerAdapter {
    @Override
    public boolean checkUrl(String url) {
        return "/user/login".equals(url);
    }

    @Override
    public HttpResponse doGet(Request request) {
        return login(request);
    }

    @Override
    public HttpResponse doPost(Request request) {
        return login(request);
    }

    private HttpResponse login(Request request) {
        User user = getUserFromRequest(request);

        User userById = DataBase.findUserById(user.getUserId());

        if (userById == null || !userById.checkPassword(user.getPassword())) {
            return new HttpResponse(HttpStatus.BAD_REQUEST, MediaType.TEXT_HTML_UTF8, "/user/login_failed.html", "logined=false; Path=/");
        }

        return new HttpResponse(HttpStatus.FOUND, MediaType.TEXT_HTML_UTF8, "/index.html", "logined=true; Path=/");
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
