package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionContext {

    private static Map<String, HttpSession> sessions;

    static {
        sessions = new HashMap<>();
    }

    public static HttpSession createSession() {
        String uuid = createUuid();
        HttpSession session = new HttpSession(uuid);
        sessions.put(uuid, session);
        return session;
    }

    private static String createUuid() {
        return UUID.randomUUID().toString();
    }

    public static HttpSession getSession(String id) {
        return sessions.get(id);
    }

    public static void remove(String id) {
        sessions.remove(id);
    }
}
