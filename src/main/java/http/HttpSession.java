package http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    private final String id;
    private Map<String, Object> attributes;

    public HttpSession(final String id) {
        this.id = id;
        this.attributes = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(final String key, final Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(final String key) {
        return attributes.get(key);
    }

    public Object removeAttribute(final String key) {
        return attributes.remove(key);
    }

    public void invalidate() {
        attributes = Collections.EMPTY_MAP;
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "id='" + id + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
