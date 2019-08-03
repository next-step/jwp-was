package controller;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.Model;
import webserver.http.RequestBody;

import java.util.Objects;
import java.util.Optional;

public class UserController {

    public static Optional createUser(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.getRequestBody();
        User user = new User(
                requestBody.get("userId"),
                requestBody.get("password"),
                requestBody.get("name"),
                requestBody.get("email")
        );

        DataBase.addUser(user);
        return Optional.of("redirect:/index.html");
    }

    public static Optional login(HttpRequest httpRequest, HttpResponse httpResponse) {
        RequestBody requestBody = httpRequest.getRequestBody();
        String userId = requestBody.get("userId");
        String password = requestBody.get("password");

        User user = DataBase.findUserById(userId);
        if (Objects.isNull(user) || !user.getPassword().equals(password)) {
            httpResponse.getCookie().set("logined", "false");
            return Optional.of("redirect:/user/login_failed.html");
        }

        httpResponse.getCookie().set("logined", "true");
        return Optional.of("redirect:/index.html");
    }

    public static Optional list(HttpRequest httpRequest, HttpResponse httpResponse) {
        String logined = httpRequest.getCookie().get("logined");
        if (StringUtils.isBlank(logined) || !Boolean.parseBoolean(logined)) {
            return Optional.of("redirect:/user/login.html");
        }

        Model model = httpResponse.getModel();
        model.set("users", DataBase.findAll());
        return Optional.of("/user/list");
    }
}
