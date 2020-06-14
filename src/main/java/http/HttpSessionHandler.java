package http;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpSessionHandler {
    private static Map<String, HttpSession> sessionMap = Maps.newConcurrentMap();

    public static void addSession(HttpSession session) {
        sessionMap.putIfAbsent(session.getId(), session);
    }

    public static HttpSession getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }
}
