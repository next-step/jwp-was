package session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManager {
    public static final String SESSION_ID = "SESSIONID";

    private static final Map<String, Session> sessionMap = new HashMap<>();

    public static String createSession() {
        HttpSession session = new HttpSession();
        String sessionId = session.getId();
        sessionMap.put(sessionId, session);
        return sessionId;
    }

    public static Session getSession(String uuid) {
        if (uuid == null) return null;
        return sessionMap.get(uuid);
    }

    public static void removeSession(String uuid) {
        Session session = sessionMap.get(uuid);
        if (session != null) {
            session.invalidate();
        }
        sessionMap.remove(uuid);
    }

    public static String getCookieHeader(String sessionId) {
        return SESSION_ID + "=" + sessionId + "; Path=/";
    }
}
