package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionStorage {

    private static Map<String, HttpSession> sessionMap = new HashMap<>();

    public static Map<String, HttpSession> getSessionMap() {
        return sessionMap;
    }

    public void addSession(HttpSession session) {
        sessionMap.put(session.getId(), session);
    }

    public HttpSession getSession(String id) {
        return sessionMap.get(id);
    }

    public void removeSession(String id) {
        sessionMap.remove(id);
    }

    public void invalidate() {
        sessionMap.clear();
    }
}