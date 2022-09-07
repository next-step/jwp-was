package service;

import db.DataBase;
import model.Cookie;
import model.http.HttpSession;

import java.util.UUID;

public class SessionUtils {

    public static HttpSession getSessionInfo(Cookie cookie) {

        if (!validationSession(cookie)) {
            final HttpSession httpSession = new HttpSession(UUID.randomUUID().toString());
            DataBase.addSession(httpSession);

            return httpSession;
        }

        return DataBase.findSession(cookie.getValue());
    }

    private static boolean validationSession(Cookie cookie) {

        if (cookie.isEmpty()) {
            return false;
        }
        final HttpSession session = DataBase.findSession(cookie.getValue());

        return session != null;
    }
}
