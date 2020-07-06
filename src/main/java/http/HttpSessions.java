package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessions {
    private static final Map<String, HttpSession> httpSessions = new HashMap<>();

    public static HttpSession addHttpSession() {
        final String sessionId = UUID.randomUUID().toString();
        httpSessions.put(sessionId, new HttpSession(sessionId));
        return httpSessions.get(sessionId);
    }

    public static HttpSession getHttpSession(final String id) {
        return httpSessions.get(id);
    }
}
