package webserver.http.session;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {
    private static Map<String, HttpSession> sessions;
    static {
        sessions = Maps.newHashMap();
    }

    public static HttpSession getSession(String sessionId) {
        if (sessionId == null) {
            return newSession();
        }

        if (!sessions.containsKey(sessionId)) {
            return newSession();
        }

        return sessions.get(sessionId);
    }

    private static HttpSession newSession() {
        HttpSession httpSession = HttpSession.newInstance(UUID.randomUUID().toString());
        sessions.put(httpSession.getId(), httpSession);

        return sessions.get(httpSession.getId());
    }

    public static boolean remove(String id) {
        sessions.remove(id);
        return !sessions.containsKey(id);
    }
}
