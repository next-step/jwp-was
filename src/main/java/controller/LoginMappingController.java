package controller;

import db.DataBase;
import model.User;
import webserver.*;

public class LoginMappingController extends RequestMappingControllerAdapter {
    @Override
    public boolean checkUrl(String url) {
        return "/user/login".equals(url);
    }

    @Override
    public Response doGet(Request request) {
        return login(request);
    }

    @Override
    public Response doPost(Request request) {
        return login(request);
    }

    private Response login(Request request) {
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
