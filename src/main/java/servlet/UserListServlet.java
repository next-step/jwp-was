package servlet;

import db.DataBase;
import view.ViewResolver;
import webserver.HttpServlet;
import webserver.Request;
import webserver.Response;
import webserver.request.Cookie;

import java.io.IOException;

import static servlet.UserLoginServlet.COOKIE_OF_LOGIN;
import static webserver.response.ResponseFactory.ok;
import static webserver.response.ResponseFactory.redirect;

public class UserListServlet implements HttpServlet {

    private static final ViewResolver viewResolver = new ViewResolver();

    @Override
    public boolean isMapping(Request request) {
        return request.matchPath("/user/list");
    }

    @Override
    public Response service(Request request) throws IOException {
        Cookie cookie = request.getCookie();

        boolean checkLogin = Boolean.valueOf(cookie.get(COOKIE_OF_LOGIN));
        if (!checkLogin) {
            return redirect("/user/login.html");
        }

        return ok(viewResolver.resolve("user/list", DataBase.findAll()));
    }
}
