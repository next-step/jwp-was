package webserver.controller;

import db.DataBase;
import model.User;
import utils.HandleBarTemplateLoader;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        boolean isLogin = isLogin(httpRequest.getHeader("Cookie"));
        if (!isLogin) {
            httpResponse.sendRedirect("/user/login.html");
            return;
        }
        Collection<User> users = DataBase.findAll();
        String userList = HandleBarTemplateLoader.load("user/list", users);
        httpResponse.forwardBody(userList);
    }

    public boolean isLogin(String cookieString) {
        if (cookieString.isEmpty()) {
            return false;
        }
        Map<String, String> cookies = new HashMap<>();
        for (String cookie : cookieString.split(";")) {
            String cookieName = cookie.split("=")[0];
            String cookieValue = cookie.split("=")[1];
            cookies.put(cookieName, cookieValue);
        }
        String loginedValue = cookies.get("logined");
        if (loginedValue == null) {
            return false;
        }
        return Boolean.parseBoolean(loginedValue);
    }
}
