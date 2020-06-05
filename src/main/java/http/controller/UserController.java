package http.controller;

import http.response.HttpResponse;
import model.User;
import utils.UserData;

public class UserController {
    public static HttpResponse saveUser(User user) {
        UserData.save(user);

        return HttpResponse.redirect("/index.html");
    }
}
