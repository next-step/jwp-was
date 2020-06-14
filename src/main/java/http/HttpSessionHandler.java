package http;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpSessionHandler {
    private static Map<String, HttpSession> sessions = Maps.newConcurrentMap();

    public static void addSession(HttpSession session) {
        sessions.putIfAbsent(session.getId(), session);
    }

    public static HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
}
