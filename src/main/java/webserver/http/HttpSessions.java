package webserver.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessions {
    private static Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    private HttpSessions() {
    }

    public static HttpSession getSession(String id) {
        HttpSession httpSession = sessions.get(id);

        if (sessionIsNew(httpSession)) {
            httpSession = new HttpSession(id);
            addSession(id, httpSession);
            return httpSession;
        }
        return httpSession;
    }

    private static boolean sessionIsNew(HttpSession httpSession) {
        return httpSession == null;
    }

    public static void addSession(String id, HttpSession httpSession) {
        sessions.put(id, httpSession);
    }

    public static void clearSession(String id) {
        sessions.remove(id);
    }

    public static void clearAllSession() {
        sessions.clear();
    }
}
