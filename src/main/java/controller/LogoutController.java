package controller;

import db.DataBase;
import db.SessionStore;
import model.Cookie;
import model.http.HttpRequest;
import model.http.HttpResponse;
import model.http.HttpSession;

public class LogoutController extends AbstractController {

    private static final String REDIRECT_PATH = "/index.html";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

        final Cookie cookie = request.getCookie("sessionId");
        response.redirect(REDIRECT_PATH);

        if (cookie == null || cookie.isEmpty()) {
            return;
        }

        final HttpSession session = SessionStore.findSession(cookie.getValue());
        if (session == null) {
            return;
        }
        session.invalidate();
    }
}
