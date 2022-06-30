package webserver.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {
    public final static String SESSION_COOKIE_NAME = "JSESSIONID";

    private final static Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession createSession() {
        final HttpSession session = new HttpSession(generateSessionId());
        sessions.put(session.getId(), session);
        return session;
    }

    private static String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public static HttpSession getSessionOrNull(final String sessionId) {
        return sessions.getOrDefault(sessionId, null);
    }

    public static void remove(final HttpSession session) {
        sessions.remove(session.getId());
    }
}
