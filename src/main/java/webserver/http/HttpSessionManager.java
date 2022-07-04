package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManager {
    private final static Map<String, HttpSession> httpSessions = new HashMap<>();

    public static HttpSession getSession(String jsessionId) {
        return httpSessions.get(jsessionId);
    }

    public static void setSession(HttpSession httpSession) {
        httpSessions.put(httpSession.getId(), httpSession);
    }
}
