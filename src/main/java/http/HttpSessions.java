package http;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpSessions {
    public static final String SESSION_ID_NAME = "JSESSIONID";
    private static final Map<String, HttpSession> sessions = Maps.newHashMap();

    public static HttpSession getSession(final String id) {
        HttpSession session = sessions.get(id);
        if (session == null) {
            session = HttpSession.from(id);
            sessions.put(id, session);
            return session;
        }
        return session;
    }

    public static void removeSession(final String id) {
        sessions.remove(id);
    }
}
