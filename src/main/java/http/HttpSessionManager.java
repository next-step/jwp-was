package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {
    private final static Map<String, HttpSession> sessionMap = new HashMap<>();


    public static HttpSession sessionCreate() {
        String sessionId = UUID.randomUUID().toString();
        sessionMap.put(sessionId, new HttpSession(sessionId));
        return sessionMap.get(sessionId);
    }

    public static HttpSession getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

}
