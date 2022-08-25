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

        response.getHeaders().add(
                Cookie.SET_COOKIE,
                "logined" + KEY_VALUE_DELIMITER + String.valueOf(isLogin) + COOKIE_DELIMITER + COOKIE_PATH + KEY_VALUE_DELIMITER + "/"
        );
        response.sendRedirect(isLogin ? "/index.html" : "/login_failed.html");

    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws InvalidHttpMethodException {
        throw new InvalidHttpMethodException();
    }
}
