package controller;

import db.DataBase;
import exception.InvalidHttpMethodException;
import model.User;
import webserver.Cookie;
import webserver.HandleBarsTemplate;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class ListUserController extends AbstractController {

    private static final String COOKIE_KEY_LOGINED = "logined";

    @Override
    void doPost(HttpRequest request, HttpResponse response) throws InvalidHttpMethodException {
        throw new InvalidHttpMethodException();
    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws Exception {
        Cookie cookie = request.getCookie();
        if (!Boolean.parseBoolean(cookie.getValue(COOKIE_KEY_LOGINED))) {
            response.sendRedirect("/login.html");
            return;
        }

        List<User> users = new ArrayList<>(DataBase.findAll());
        byte[] loaded = HandleBarsTemplate.load("user/list", users).getBytes();

        response.forwardBody(loaded);
        return;
    }
}
