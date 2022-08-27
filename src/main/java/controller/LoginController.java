package controller;

import db.DataBase;
import exception.InvalidHttpMethodException;
import model.User;
import webserver.Cookie;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Objects;

import static webserver.Cookie.*;

public class LoginController extends AbstractController {

    @Override
    void doPost(HttpRequest request, HttpResponse response) throws Exception {
        User loginUser = DataBase.findUserById(request.getBodyParameter("userId"));
        boolean isLogin = !Objects.isNull(loginUser) && request.getBodyParameter("password").equals(loginUser.getPassword());

        response.getHeaders().getCookie().setCookie(
                Cookie.SET_COOKIE,
                "logined" + KEY_VALUE_DELIMITER + String.valueOf(isLogin) + COOKIE_DELIMITER + COOKIE_PATH + KEY_VALUE_DELIMITER + "/"
        );

        if (!isLogin) {
            response.sendRedirect("/login_failed.html");
            return;
        }

        response.sendRedirect("/index.html");
    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws InvalidHttpMethodException {
        throw new InvalidHttpMethodException();
    }
}
