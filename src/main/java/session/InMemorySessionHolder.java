package session;

import java.util.HashMap;
import java.util.Map;

public class InMemorySessionHolder {
    private static final Map<String, HttpSession> SESSIONS = new HashMap<>();

    public InMemorySessionHolder() {}


    public void save(final HttpSession session) {
        SESSIONS.put(session.getId(), session);
    }

    public HttpSession load(final String sessionId) {
        return SESSIONS.get(sessionId);
    }
}
