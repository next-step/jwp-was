package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class Sessions {
    private static Map<String, HttpSession> sessions = new HashMap<>();

    public Sessions(Map<String, HttpSession> sessions) {
        this.sessions = sessions;
    }

    public static HttpSession findById(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void addSession(HttpSession session) {
        sessions.put(session.getId(), session);
    }
}
