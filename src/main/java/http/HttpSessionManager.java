package http;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {
    private final static ConcurrentHashMap<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    public static HttpSession sessionCreate() {
        String sessionId = UUID.randomUUID().toString();
        sessionMap.put(sessionId, new HttpSession(sessionId));
        return sessionMap.get(sessionId);
    }

    public static HttpSession getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

}
