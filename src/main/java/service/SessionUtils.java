package service;

import db.DataBase;
import db.SessionStore;
import model.Cookie;
import model.http.HttpSession;

import java.util.UUID;

public class SessionUtils {

    public static HttpSession getSessionInfo(Cookie cookie) {

        if (!validationSession(cookie)) {
            final HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
            SessionStore.addSession(httpSession);

            return httpSession;
        }

        return SessionStore.findSession(cookie.getValue());
    }

    private static boolean validationSession(Cookie cookie) {

        if (cookie.isEmpty()) {
            return false;
        }
        final HttpSession session = SessionStore.findSession(cookie.getValue());

        return session != null;
    }
}
