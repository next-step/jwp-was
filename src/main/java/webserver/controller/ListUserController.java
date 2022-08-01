package webserver.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.Headers;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.template.UserList;

import java.util.ArrayList;
import java.util.Collection;

public class ListUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

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
