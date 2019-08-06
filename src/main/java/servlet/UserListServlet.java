package servlet;

import db.DataBase;
import view.ViewResolver;
import webserver.HttpServlet;
import webserver.Request;
import webserver.Response;
import webserver.request.Cookie;

import java.io.IOException;

import static servlet.UserLoginServlet.COOKIE_OF_LOGIN;

public class UserListServlet implements HttpServlet {

    private static final ViewResolver viewResolver = new ViewResolver();
    private static final String URL = "/user/list";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    public void service(Request request, Response response) throws IOException {
        Cookie cookie = request.getCookie();

        boolean checkLogin = Boolean.valueOf(cookie.get(COOKIE_OF_LOGIN));
        if (!checkLogin) {
            response.redirect("/index.html");
            return;
        }

        String content = viewResolver.resolve("user/list", DataBase.findAll());
        response.ok(content.getBytes());
    }
}
