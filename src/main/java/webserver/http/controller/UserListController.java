package webserver.http.controller;

import db.DataBase;
import model.User;
import utils.TemplateUtils;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!httpRequest.isLogined()) {
            httpResponse.redirect("/user/login.html");
            return;
        }
        Collection<User> users = DataBase.findAll();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("users", users);

        httpResponse.ok(TemplateUtils.createHandlarsView("user/list", userMap));
    }
}
