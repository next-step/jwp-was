package webserver.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDatabase {
    private static Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    private SessionDatabase() {
    }

    public static void save(String id, HttpSession httpSession) {
        sessions.put(id, httpSession);
    }

    public static HttpSession findById(String id) {
        return sessions.get(id);
    }

    public static void deleteById(String id) {
        sessions.remove(id);
    }
}