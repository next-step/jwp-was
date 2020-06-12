package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpSessionManager {

    public static final String SESSION_NAME = "KSESSIONID";
    private final Map<String, HttpSession> sessions = new HashMap<>();

    public String createSession() {
        return createSession(UUID.randomUUID().toString()).getId();
    }

    public HttpSession createSession(String id) {
        final HttpSession session = new HttpSession(id);
        sessions.put(id, session);
        return session;
    }

    public HttpSession getSession(String id) {
        return sessions.get(id);
    }

    public void removeSession(String id) {
        sessions.remove(id);
    }
}
