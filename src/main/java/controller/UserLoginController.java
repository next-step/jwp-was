package controller;

import db.DataBase;
import model.User;
import webserver.Controller;
import webserver.Request;
import webserver.Response;

import javax.swing.*;
import java.io.IOException;

public class UserLoginController extends AbstractController {

    static final String COOKIE_OF_LOGIN = "logined";

    private static final String URL = "/user/login";

    public static boolean isMapping(Request request) {
        return request.matchPath(URL);
    }

    @Override
    void doGet(Request request, Response response) throws Exception {
        response.notFound();
    }

    @Override
    void doPost(Request request, Response response) throws Exception {
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