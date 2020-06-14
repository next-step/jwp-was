package session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManager {
    private static final Map<String, Session> sessionMap = new HashMap<>();

    private static String createSession() {
        HttpSession session = new HttpSession();
        String sessionId = session.getId();
        sessionMap.put(sessionId, session);
        return sessionId;
    }

    private static String setCookieHeader(String sessionId) {
        return "Set-Cookie: " + sessionId;
    }
}
