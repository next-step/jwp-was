package http.controller;

import db.DataBase;
import http.requests.HttpRequest;
import http.requests.parameters.Cookie;
import http.responses.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (isSignedIn(request)) {
            final Collection<User> users = DataBase.findAll();
            final Map<String, Object> map = new HashMap<>();
            map.put("users", users);
            log.debug("map to be rendered: {}", map);
            response.render(request.getPath(), map);
        }
        response.sendRedirect("/user/login.html");
    }

    private boolean isSignedIn(HttpRequest request) {
        final Cookie cookie = request.getCookie();
        return Boolean.parseBoolean(cookie.getValue("logined"));
    }
}
