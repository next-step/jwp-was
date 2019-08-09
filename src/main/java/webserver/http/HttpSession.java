package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private final String id;
    private Map<String, Object> sessions = new HashMap<>();

    public HttpSession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String key, Object value) {
        sessions.put(key, value);
    }

    public Object getAttribute(String key) {
        return sessions.get(key);
    }

    public void removeAttributes(String key) {
        sessions.remove(key);
    }

    public void invalidate() {
        sessions.clear();
    }

}
