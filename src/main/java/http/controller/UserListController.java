package http.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.util.Collection;

public class UserListController extends AbstractController {
    @Override
    public void doGet(final HttpRequest request, final HttpResponse response) {
        String isLogin = request.getCookies().getValue("login");
        if (isLogin == null || isLogin.equals("false")) {
            response.sendRedirect("/user/login.html");
            return;
        }
        final Collection<User> userList = DataBase.findAll();
        request.addAttribute("users", userList);
        response.templateForward();
    }
}
