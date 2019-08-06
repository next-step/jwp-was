package servlet;

import db.DataBase;
import model.User;
import webserver.HttpServlet;
import webserver.Request;
import webserver.Response;

import java.io.IOException;

public class UserLoginServlet implements HttpServlet {

    static final String COOKIE_OF_LOGIN = "logined";

    private static final String URL = "/user/login";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User userById = DataBase.findUserById(userId);

        if (userById == null || !userById.checkPassword(password)) {
            loginFail(response);
            return;
        }
        loginSuccess(response);
    }

    private void loginSuccess(Response response) throws IOException {
        response.setCookie(COOKIE_OF_LOGIN + "=true; Path=/");
        response.redirect("/index.html");
    }

    private void loginFail(Response response) throws IOException {
        response.setCookie(COOKIE_OF_LOGIN + "=false; Path=/");
        response.redirect("/user/login_failed.html");
    }
}