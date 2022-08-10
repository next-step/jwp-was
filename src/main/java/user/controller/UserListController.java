package user.controller;

import db.DataBase;
import user.model.User;
import webserver.controller.AbstractController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.HttpSessionManager;

import java.util.Collection;

public class UserListController extends AbstractController {
    private static final String LOGINED_KEY = "logined";

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        final HttpSession httpSession = HttpSessionManager.getHttpSession(httpRequest);
        final Boolean logined = httpSession.getAttribute(LOGINED_KEY, Boolean.class);
        if (logined == null || !logined) {
            return HttpResponse.sendRedirect("/index.html");
        }

        final Collection<User> users = DataBase.findAll();

        return HttpResponse.forward("user/list", users);
    }
}
