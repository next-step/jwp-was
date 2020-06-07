package http.controller;

import http.HttpStatus;
import http.response.HttpResponse;
import model.user.User;
import model.user.UserRequestView;
import utils.TemplateUtils;
import utils.UserData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserController {
    public static HttpResponse saveUser(User user) {
        UserData.save(user);

        return HttpResponse.redirect("/index.html");
    }

    public static HttpResponse login(UserRequestView userRequestView) {
        User user = UserData.getUser(userRequestView.getUserId());

        if (Objects.isNull(user) || !user.login(userRequestView.getPassword())) {
            HttpResponse response = HttpResponse.redirect("/user/login_failed.html");
            response.setCookie("logined=false; Path=/");
            return response;
        }

        HttpResponse response = HttpResponse.redirect("/index.html");
        response.setCookie("logined=true; Path=/");
        return response;
    }

    public static HttpResponse list() throws IOException {
        Map<String, Object> userMap = new HashMap<>();
        List<User> users = UserData.findAll();
        userMap.put("users", users);

        String listPage = TemplateUtils.getTemplate("user/list", userMap);
        return HttpResponse.body(HttpStatus.OK, listPage.getBytes(), "text/html");
    }
}
