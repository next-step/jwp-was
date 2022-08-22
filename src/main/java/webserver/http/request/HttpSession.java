package webserver.http.request;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    public static final String SESSION_ID_KEY = "JSESSIONID";
    private final UUID id;
    private final Map<String, Object> attributes = new HashMap<>();

    public HttpSession(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public void invalidate() {
        attributes.clear();
    }
}
