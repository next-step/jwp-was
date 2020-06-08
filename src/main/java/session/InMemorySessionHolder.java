package session;

import java.util.HashMap;
import java.util.Map;

public class InMemorySessionHolder {
    private static final Map<String, HttpSession> SESSIONS = new HashMap<>();

    public InMemorySessionHolder() {}


    public void add(final String sessionId, final HttpSession session) {
        SESSIONS.put(sessionId, session);
    }

    public HttpSession get(final String sessionId) {
        return SESSIONS.get(sessionId);
    }
}
