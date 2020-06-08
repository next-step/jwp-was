package http.controller;

import db.DataBase;
import http.requests.HttpRequest;
import http.requests.parameters.Cookie;
import http.responses.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class UserListController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (isSignedIn(request)) {
            final Collection<User> users = DataBase.findAll();
            log.debug("user list: {}", users);
            response.addAttribute("users", users);
            response.renderTemplate("/user/list");
            return;
        }
        response.sendRedirect("/user/login.html");
    }

    private boolean isSignedIn(HttpRequest request) {
        final Cookie cookie = request.getCookie();
        return Boolean.parseBoolean(cookie.getValue("logined"));
    }
}
