package controller;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ModelAndView;

import java.util.Objects;
import java.util.Optional;

public class UserController {

    private static final String COOKIE_LOGINED_KEY = "logined";
    private static final String COOKIE_LOGINED_SUCCESS_VALUE = "true";
    private static final String COOKIE_LOGINED_FAIL_VALUE = "true";

    public static Optional createUser(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(
                httpRequest.bodyValue("userId"),
                httpRequest.bodyValue("password"),
                httpRequest.bodyValue("name"),
                httpRequest.bodyValue("email")
        );

        DataBase.addUser(user);
        return Optional.of("redirect:/index.html");
    }

    public static Optional login(HttpRequest httpRequest, HttpResponse httpResponse) {
        String userId = httpRequest.bodyValue("userId");
        String password = httpRequest.bodyValue("password");

        User user = DataBase.findUserById(userId);
        if (Objects.isNull(user) || !user.isEqualsPassword(password)) {
            httpResponse.setCookie(COOKIE_LOGINED_KEY, COOKIE_LOGINED_FAIL_VALUE);
            return Optional.of("redirect:/user/login_failed.html");
        }

        httpResponse.setCookie(COOKIE_LOGINED_KEY, COOKIE_LOGINED_SUCCESS_VALUE);
        return Optional.of("redirect:/index.html");
    }

    public static Optional list(HttpRequest httpRequest, HttpResponse httpResponse) {
        String logined = httpRequest.cookieValue(COOKIE_LOGINED_KEY);
        if (StringUtils.isBlank(logined) || !Boolean.parseBoolean(logined)) {
            return Optional.of("redirect:/user/login.html");
        }

        ModelAndView model = httpResponse.getModelAndView();
        model.setAttribute("users", DataBase.findAll());
        return Optional.of("/user/list");
    }
}
