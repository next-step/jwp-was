package utils;

import model.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static Map<String, HttpSession> sessions = null;

    private SessionManager() {
    }

    public static Map<String, HttpSession> getSessions() {
        if (sessions == null) {
            sessions = new HashMap<>();
        }

        return sessions;
    }

    public static String setSession(String attributeName, Object attribute) {
        if (sessions == null) {
            sessions = new HashMap<>();
        }

        HttpSession httpSession = new HttpSession(UUID.randomUUID());
        httpSession.setAttribute(attributeName, attribute);

        sessions.put(httpSession.getId(), httpSession);

        return httpSession.getId();
    }

    public static String setSession(String sessionId, String attributeName, Object attribute) {
        if (sessions.containsKey(sessionId)) {
            HttpSession oldSession = sessions.get(sessionId);
            oldSession.invalidate();
            oldSession.setAttribute(attributeName, attribute);

            sessions.replace(sessionId, oldSession);

            return oldSession.getId();
        }

        HttpSession httpSession = new HttpSession(UUID.randomUUID());
        httpSession.setAttribute(attributeName, attribute);

        sessions.put(httpSession.getId(), httpSession);

        return httpSession.getId();
    }
}
