package controller;

import db.DataBase;
import view.ViewResolver;
import webserver.Request;
import webserver.Response;
import webserver.request.Cookie;
import webserver.response.HttpResponse;

import static controller.UserLoginController.COOKIE_OF_LOGIN;

public class UserListController extends AbstractController {

    private static final ViewResolver viewResolver = new ViewResolver();
    private static final String URL = "/user/list";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    Response doGet(Request request) throws Exception {
        Cookie cookie = request.getCookie();

        boolean checkLogin = Boolean.valueOf(cookie.get(COOKIE_OF_LOGIN));
        if (!checkLogin) {
            return HttpResponse.redirect("/index.html");
        }

        String content = viewResolver.resolve("user/list", DataBase.findAll());
        return HttpResponse.ok(content.getBytes());
    }

    @Override
    Response doPost(Request request) {
        return HttpResponse.notFound();
    }
}
