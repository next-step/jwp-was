package webserver.http.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionStore {
    public static final String SESSION_ID_KEY = "JWP-SESSION-ID";
    private static final Map<HttpSessionId, HttpSession> sessions = new ConcurrentHashMap<>();

    public static HttpSession getSession(HttpSessionId id) {
        if (sessions.containsKey(id)) {
            return sessions.get(id);
        }

        HttpSession session = new HttpSession(id);
        sessions.put(id, session);

        return session;
    }

    public static void remove(HttpSessionId id) {
        sessions.remove(id);
    }
}
