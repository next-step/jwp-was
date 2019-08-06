package servlet;

import db.DataBase;
import model.User;
import webserver.HttpServlet;
import webserver.Request;
import webserver.Response;

import static webserver.response.ResponseFactory.redirect;

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

        if (userById == null || !userById.checkPassword(password)) {
            return loginFail();
        }
        return loginSuccess();
    }

    private Response loginSuccess() {
        Response response = redirect("/index.html");
        response.setCookie(COOKIE_OF_LOGIN + "=true; Path=/");
        return response;
    }

    private Response loginFail() {
        Response response = redirect("/user/login_failed.html");
        response.setCookie(COOKIE_OF_LOGIN + "=false; Path=/");
        return response;
    }
}