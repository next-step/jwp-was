package was.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {
    public static final String SESSION_ID_NAME = "JSESSIONID";

    private static final Map<String, HttpSession> sessions = new HashMap<>();

    public static HttpSession getSession(String id) {
        HttpSession session = sessions.get(id);
        if (session == null) {
            session = new HttpSession(id);
            sessions.put(id, session);
            return session;
        }

        return session;
    }

    static void remove(String id) {
        sessions.remove(id);
    }
}
