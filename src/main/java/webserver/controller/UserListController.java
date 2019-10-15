package webserver.controller;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseResolver;
import webserver.http.session.HttpSession;
import webserver.http.session.HttpSessionManager;
import webserver.http.view.ViewHandler;

import java.io.IOException;
import java.util.List;

public class UserListController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public HttpResponse handle(HttpRequest request) {
        HttpSession httpSession = HttpSessionManager.getSession(request.getSessionId());
        if (httpSession.getAttribute("user") == null) {
            return HttpResponseResolver.forward("text/html", "/user/login.html");
        }

        try {
            List<User> users = DataBase.findAll();
            String listPage = ViewHandler.render(request.getHttpRequestLine(), users);
            return HttpResponseResolver.forward("text/html", listPage.getBytes());
        } catch (IOException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }
}