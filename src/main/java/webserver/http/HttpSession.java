package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private Map<String, Object> sessions = new HashMap<>();

    private String id;

    public HttpSession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        sessions.put(name, value);
    }

    public Object getAttribute(String name) {
        return sessions.get(name);
    }

    public void removeAttribute(String name) {
        sessions.remove(name);
    }

    public void invalidate() {
        SessionManager.remove(id);
    }
}
