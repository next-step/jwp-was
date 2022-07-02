package controller;

import db.DataBase;
import http.*;
import model.User;

public class LoginMappingController extends RequestMappingControllerAdapter {
    @Override
    public boolean checkUrl(String url) {
        return "/user/login".equals(url);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        return login(httpRequest);
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        return login(httpRequest);
    }

    private HttpResponse login(HttpRequest httpRequest) {
        User user = getUserFromRequest(httpRequest);

        User userById = DataBase.findUserById(user.getUserId());

        if (userById == null || !userById.checkPassword(user.getPassword())) {
            return new HttpResponse(HttpStatus.BAD_REQUEST, MediaType.TEXT_HTML_UTF8, "/user/login_failed.html", "logined=false; Path=/");
        }

        return new HttpResponse(HttpStatus.FOUND, MediaType.TEXT_HTML_UTF8, "/index.html", "logined=true; Path=/");
    }

    private User getUserFromRequest(HttpRequest httpRequest) {
        QueryString queryString = httpRequest.getRequestLine().toQueryString();

        if (httpRequest.getRequestLine().getMethod() == RequestMethod.POST) {
            queryString = QueryString.parse(httpRequest.getRequestBody());
        }

        return new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );
    }
}
