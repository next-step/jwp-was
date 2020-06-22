package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private UUID sessionId;
    private Map<String, Object> attributes = new HashMap<>();

    public HttpSession(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public String getId() {
        return sessionId.toString();
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }
}
