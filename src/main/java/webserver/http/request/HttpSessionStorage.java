package webserver.http.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpSessionStorage {
    private static final Map<UUID, HttpSession> SESSIONS = new HashMap<>();

    public static HttpSession getSession(UUID sessionId) {
        HttpSession session = SESSIONS.get(sessionId);
        if (Objects.isNull(session)) {
            session = new HttpSession(sessionId);
            SESSIONS.put(sessionId, session);
        }
        return session;
    }
}
