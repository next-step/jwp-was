package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private String id;
    private Map<String, Object> sessions;

    public HttpSession(String id) {
        this.id = id;
        this.sessions = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object attribute) {
        sessions.put(name, attribute);
    }

    public Object getAttribute(String name) {
        return sessions.get(name);
    }

    public void removeAttribute(String name) {
        sessions.remove(name);
    }

    public void invalidate() {
        sessions.clear();
    }
}

