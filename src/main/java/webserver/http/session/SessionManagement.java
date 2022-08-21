package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManagement {
    private static final Map<String, HttpSession> SESSION = new HashMap<>();

    public static HttpSession createSession() {
        HttpSession session = new HttpSession(new HashMap<>());
        SESSION.put(session.getId(), session);

        return session;
    }

    public static HttpSession getSession(String sessionId) {
        return SESSION.get(sessionId);
    }

}
