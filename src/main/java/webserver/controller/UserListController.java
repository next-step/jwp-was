package webserver.controller;

import db.DataBase;
import model.User;
import utils.StringUtils;
import webserver.request.HttpRequest;
import webserver.request.RequestHeaders;
import webserver.response.HttpResponse;

import java.util.Collection;

public class UserListController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        RequestHeaders requestHeaders = request.getRequestHeaders();
        String cookie = requestHeaders.get("Cookie");
        if (StringUtils.isNotBlank(cookie) && cookie.contains("logined=true")) {
            Collection<User> users = DataBase.findAll();
            response.addBody(users);
            response.response200();
            return;
        }
        String location = "http://" + request.getRequestHeaders().get("Host") + "/user/login.html";
        response.response302(location);
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {

    }
}
