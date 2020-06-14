package http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {

    public static Map<String, HttpSession> SESSIONS = new ConcurrentHashMap<>();

    public static HttpSession getSession(String id) {
        return SESSIONS.get(id);
    }

    public static boolean isAuthentication(String id) {
        return SESSIONS.containsKey(id);
    }

    public static void addSession(HttpSession httpSession) {
        SESSIONS.put(httpSession.getId(), httpSession);
    }

}
