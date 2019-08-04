package servlet;

import db.DataBase;
import model.User;
import webserver.HttpHeaders;
import webserver.Request;
import webserver.Response;
import webserver.HttpServlet;

public class UserLoginServlet implements HttpServlet {

    static final String COOKIE_OF_LOGIN = "logined";

    @Override
    public boolean isMapping(Request request) {
        return request.matchPath("/user/login");
    }

    @Override
    public Response service(Request request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User userById = DataBase.findUserById(userId);

        HttpHeaders httpHeaders = new HttpHeaders();
        if (userById == null || !userById.checkPassword(password)) {
            return loginFail(httpHeaders);
        }
        return loginSuccess(httpHeaders);
    }

    private Response loginFail(HttpHeaders httpHeaders) {
        Response response = Response.redirect("/user/login_failed.html");
        httpHeaders.setCookie(COOKIE_OF_LOGIN + "=false; Path=/");
        return response;
    }

    private Response loginSuccess(HttpHeaders httpHeaders) {
        Response response = Response.redirect("/index.html");
        httpHeaders.setCookie(COOKIE_OF_LOGIN + "=true; Path=/");
        return response;
    }
}
