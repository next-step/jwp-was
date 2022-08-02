package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.Headers;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.template.UserList;

import java.util.ArrayList;
import java.util.Collection;

public class ListUserController extends AbstractController {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        final Headers headers = request.getHeaders();
        if (!headers.isLogin()) {
            response.sendRedirect("/user/login.html");
            return;
        }

        final UserList users = getUsers();

        final String template = users.generateUserListTemplate();
        response.forwardBody(template);
    }

    private UserList getUsers() {
        final Collection<User> users = DataBase.findAll();
        return new UserList(new ArrayList<>(users));
    }
}
