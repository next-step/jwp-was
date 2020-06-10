package http;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpSessionHandler {
    private static Map<String, HttpSession> sessionMap = Maps.newHashMap();

    public static void addSession(HttpSession session) {
        sessionMap.put(session.getId(), session);
    }

    public static HttpSession getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }
}
