package session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static Map<String, HttpSession> sessionStore = new HashMap<>();

    public static HttpSession createSession() {
        HttpSession session = HttpSession.from(SessionAttribute.empty());
        sessionStore.put(session.getId(), session);

        return session;
    }

    public static HttpSession findBySessionId(String id) {
        return sessionStore.get(id);
    }
}
