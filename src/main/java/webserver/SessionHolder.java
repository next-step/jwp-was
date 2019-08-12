package webserver;

import webserver.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionHolder {
    private static Map<String, HttpSession> sessions;

    static {
        sessions = new HashMap<>();
    }

    public static HttpSession getSession(String sessionId) {
        if (sessionId == null) {
            return createSession();
        }

        return sessions.get(sessionId);
    }

    private static HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(sessionId);
        sessions.put(sessionId, httpSession);

        return sessions.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
