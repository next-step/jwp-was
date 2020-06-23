package http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpSession {

    private final String sessionId;
    private final Map<String, Object> data = new HashMap<>();

    public HttpSession() {
        this(UUID.randomUUID().toString());
    }

    public HttpSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getId() {
        return this.sessionId;
    }

    public void setAttribute(String name, Object value) {
        this.data.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.data.get(name);
    }

    public void removeAttribute(String name) {
        this.data.remove(name);
    }

    public void invalidate() {
        this.data.clear();
    }
}
