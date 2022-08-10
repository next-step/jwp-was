package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.template.UserList;

import java.util.ArrayList;
import java.util.Collection;

public class ListUserController extends AbstractController {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        HttpSession session = request.getSession();
        if (!isLogin(session)) {
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


    private boolean isLogin(HttpSession httpSession) {
        Object user = httpSession.getAttribute("user");
        return user != null;
    }
}
