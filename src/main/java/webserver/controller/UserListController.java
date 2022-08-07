package webserver.controller;

import db.DataBase;
import model.User;
import utils.IOUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.util.HandlebarsObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public class UserListController extends GetController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        String headerValue = request.getHeader("Cookie");

        try {
            String path = "";
            if (headerValue == null || headerValue.equals("logined=false")) {
                path = IOUtils.loadFileFromClasspath("./templates/user/login.html");
            } else if (headerValue.equals("logined=true Path=/")) {
                HandlebarsObject handlebarObj = HandlebarsObject.createHandlebarObject("/templates", ".html");

                Collection<User> users = DataBase.findAll();
                path = handlebarObj.applyTemplate("user/list", users);
            }
            response.forward(path);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
