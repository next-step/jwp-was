package http.common;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession getOrCreate(String sessionId) {
        if (!sessions.containsKey(sessionId)) {
            return create();
        }
        return sessions.get(sessionId);
    }

    private static HttpSession create() {
        HttpSession newSession = new HttpSession();
        sessions.put(newSession.getId(), newSession);
        return sessions.get(newSession.getId());
    }

    public static void remove(String sessionId) {
        sessions.remove(sessionId);
    }

    public static void clear() {
        sessions.clear();
    }

    public static int size() {
        return sessions.size();
    }
}
