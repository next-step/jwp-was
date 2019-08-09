package controller;

import db.DataBase;
import model.User;
import webserver.Request;
import webserver.Response;
import webserver.response.HttpResponse;

public class UserLoginController extends AbstractController {

    static final String COOKIE_OF_LOGIN = "logined";

    private static final String URL = "/user/login";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    Response doGet(Request request) {
        return HttpResponse.notFound();
    }

    @Override
    Response doPost(Request request) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User userById = DataBase.findUserById(userId);

        if (userById == null || !userById.checkPassword(password)) {
            return loginFail();
        }
        return loginSuccess();
    }

    private Response loginSuccess() {
        Response response = HttpResponse.redirect("/index.html");
        response.setCookie(COOKIE_OF_LOGIN + "=true; Path=/");
        return response;
    }

    private Response loginFail() {
        Response response = HttpResponse.redirect("/user/login_failed.html");
        response.setCookie(COOKIE_OF_LOGIN + "=false; Path=/");
        return response;
    }
}