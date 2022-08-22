package controller;

import db.DataBase;
import view.TemplateView;
import view.View;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.request.Cookie;

import java.util.ArrayList;

public class ListUserController extends AbstractController {
    @Override
    void doPost(HttpRequest request, HttpResponse response) throws Exception {
        response.response404();
    }

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws Exception {
        Cookie cookie = request.getCookie();
        boolean logined = Boolean.parseBoolean(cookie.get("logined"));
        if (!logined) {
            response.sendRedirect("/user/login.html");
            return;
        }

        request.setAttribute("users", new ArrayList<>(DataBase.findAll()));

        View view = new TemplateView();
        view.render(request, response, "user/list");
    }
}
