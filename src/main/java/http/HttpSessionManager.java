package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpSessionManager {

    public static final String SESSION_NAME = "KSESSIONID";
    private static final Map<String, HttpSession> sessions = new HashMap<>();

    private HttpSessionManager() {
    }

    public static String createSession() {
        return createSession(UUID.randomUUID().toString()).getId();
    }

    public static HttpSession createSession(String id) {
        final HttpSession session = new HttpSession(id);
        sessions.put(id, session);
        return session;
    }

    public static HttpSession getSession(String id) {
        return sessions.get(id);
    }

    public static void removeSession(String id) {
        sessions.remove(id);
    }
}
