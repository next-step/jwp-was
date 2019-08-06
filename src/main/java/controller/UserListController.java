package controller;

import db.DataBase;
import view.ViewResolver;
import webserver.Request;
import webserver.Response;
import webserver.request.Cookie;

import static controller.UserLoginController.COOKIE_OF_LOGIN;

public class UserListController extends AbstractController {

    private static final ViewResolver viewResolver = new ViewResolver();
    private static final String URL = "/user/list";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    void doGet(Request request, Response response) throws Exception {
        Cookie cookie = request.getCookie();

        boolean checkLogin = Boolean.valueOf(cookie.get(COOKIE_OF_LOGIN));
        if (!checkLogin) {
            response.redirect("/index.html");
            return;
        }

        String content = viewResolver.resolve("user/list", DataBase.findAll());
        response.ok(content.getBytes());
    }

    @Override
    void doPost(Request request, Response response) throws Exception {
        response.notFound();
    }
}
