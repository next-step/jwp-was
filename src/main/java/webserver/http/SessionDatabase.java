package webserver.http;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionDatabase {
    private static Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    private SessionDatabase() {
    }

    public static String save() {
        String newId = UUID.randomUUID().toString();
        sessions.put(newId, new HttpSession(newId));
        return newId;
    }

    public static HttpSession findById(String id) {
        return sessions.get(id);
    }

    public static void deleteById(String id) {
        sessions.remove(id);
    }
}