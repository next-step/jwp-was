package db;

import com.google.common.collect.Maps;
import model.http.HttpSession;

import java.util.Map;

public class SessionStore {
    private static Map<String, HttpSession> sessions = Maps.newHashMap();

    public static HttpSession findSession(String value) {
        return sessions.get(value);
    }

    public static void addSession(HttpSession session) {
        sessions.put(session.getId(), session);
    }

    public static boolean existSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }
}
