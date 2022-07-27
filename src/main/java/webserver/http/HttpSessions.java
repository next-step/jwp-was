package webserver.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessions {

    private static Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public HttpSession getSession(String id) {
        HttpSession session = sessions.get(id);

        if (session == null) {
            session = new HttpSession(id);
            sessions.put(id, session);
            return session;
        }

        return session;
    }
}
