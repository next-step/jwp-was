package controller;

import db.DataBase;
import http.*;
import model.User;
import model.Users;
import template.HandleBars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class UserController {

    public static void join(HttpRequest httpRequest, HttpResponse httpResponse) {
        QueryStrings queryStrings = httpRequest.getQueryString();

        User user = new User(queryStrings.getValue("userId"), queryStrings.getValue("password"), queryStrings.getValue("name"), queryStrings.getValue("email"));
        DataBase.addUser(user);

        httpResponse.response302ToIndexPage();
    }

    public static void login(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isLoginSuccess(httpRequest.getQueryString())) {
            httpResponse.response302ToLoginSuccessIndexPage();
        } else {
            httpResponse.response302ToLoginFailedPage();
        }
    }

    private static boolean isLoginSuccess(QueryStrings queryStrings) {
        String userId = queryStrings.getValue("userId");
        String password = queryStrings.getValue("password");

        User savedUser = DataBase.findUserById(userId);
        return savedUser.matchPassword(password);
    }

    public static void findAll(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Optional<String> maybeCookies = httpRequest.getHeaderValue(HeaderName.COOKIE);

        if (maybeCookies.isPresent()) {
            Cookies cookies = Cookies.createCookies(maybeCookies.get());
            Optional<Cookie> maybeLoginedCookie = cookies.getValue("logined");

            if (maybeLoginedCookie.isPresent()) {
                Cookie loginedCookie = maybeLoginedCookie.get();
                String logined = loginedCookie.getValue();

                if (Boolean.parseBoolean(logined)) {
                    Users users = new Users(new ArrayList<>(DataBase.findAll()));

                    String dynamicCreatedHtml = HandleBars.getHTML(httpRequest.getPath(), users);
                    byte[] body = dynamicCreatedHtml.getBytes();

                    httpResponse.response200Header("text/html", body.length);
                    httpResponse.responseBody(body);
                }
            }
        }

        httpResponse.response302ToLoginPage();
    }
}
