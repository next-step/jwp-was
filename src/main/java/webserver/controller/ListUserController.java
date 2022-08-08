package webserver.controller;

import db.DataBase;
import model.User;
import utils.HandleBarTemplateLoader;
import webserver.http.HttpCookie;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

import java.io.IOException;
import java.util.Collection;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        HttpSession session = httpRequest.getSession();
        if (session == null) {
            httpResponse.sendRedirect("/user/login.html");
            return;
        }
        Collection<User> users = DataBase.findAll();
        String userList = HandleBarTemplateLoader.load("user/list", users);
        httpResponse.forwardBody(userList);
    }
}
