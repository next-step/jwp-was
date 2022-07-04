package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {

    private static final Map<String, HttpSession> sessionMap = new HashMap<>();

    public static HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();
        HttpSession newSession = HttpSession.from(sessionId);
        sessionMap.put(sessionId, newSession);
        return newSession;
    }

    public static HttpSession getSession(String sessionId) {
        return sessionMap.getOrDefault(sessionId, null);
    }
}
