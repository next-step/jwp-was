package controller;

import db.DataBase;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import webserver.controller.AbstractController;
import webserver.exceptions.SessionNotExistException;
import webserver.session.HttpSession;

public class UserLoginController extends AbstractController {

    private UserLoginController() {
    }

    private static class Singleton {
        private static final UserLoginController instance = new UserLoginController();
    }

    public static UserLoginController getInstance() {
        return Singleton.instance;
    }

    @Override
    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        final String userId = httpRequest.getParameter("userId");
        final String password = httpRequest.getParameter("password");

        final User user = DataBase.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저Id 입니다."));

        if (user.matchPassword(password)) {
            httpResponse.sendRedirect("/index.html");
            HttpSession session = httpRequest.getSession(true)
                    .orElseThrow(SessionNotExistException::new);
            session.setAttribute("isAuthenticated", true);
        } else {
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }

}
