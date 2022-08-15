package webserver.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionStorage {

    public static final String JSESSIONID = "JSESSIONID";

    private static Map<String, HttpSession> httpSessions = new ConcurrentHashMap<>();

    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    public static HttpSession getSession(String id) {
        HttpSession session = httpSessions.get(id);
        if (session == null) {
            return createSession(id);
        }
        return session;
    }

    public static void addSession(HttpSession session) {
        httpSessions.put(session.getId(), session);
    }

    public static HttpSession createSession(String id) {
        HttpSession session = new HttpSession(id);
        httpSessions.put(id, session);
        return session;
    }

    public static void remove(String id) {
        httpSessions.remove(id);
    }

    public static void invalidate() {
        httpSessions.clear();
    }
}
