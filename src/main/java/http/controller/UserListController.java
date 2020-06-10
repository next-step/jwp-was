package http.controller;

import db.DataBase;
import http.requests.HttpRequest;
import http.responses.HttpResponse;
import http.session.HttpSession;
import http.session.SessionManager;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

public class UserListController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        final HttpSession currentSession = getCurrentSession(request);
        log.debug("UserListController#service - {}", currentSession);
        final boolean logined = Optional.ofNullable((Boolean) currentSession.getAttribute("logined")).orElse(false);
        if (logined) {
            final Collection<User> users = DataBase.findAll();
            log.debug("user list: {}", users);
            response.addAttribute("users", users);
            response.renderTemplate("/user/list");
            return;
        }
        response.sendRedirect("/user/login.html");
    }

    private HttpSession getCurrentSession(HttpRequest request) {
        final String sessionId = request.getCookie().getValue(SessionManager.SESSION_NAME);
        log.debug("UserListController#getCurrentSession - {}", sessionId);
        return SessionManager.getSession(sessionId);
    }
}
