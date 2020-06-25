package http;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpSession {
    private final String id;
    private final Map<String, Object> values = Maps.newHashMap();

    private HttpSession(final String id) {
        this.id = id;
    }

    public static HttpSession from(final String id) {
        return new HttpSession(id);
    }

    public String getId() {
        return this.id;
    }

    public void setAttribute(final String key, final Object value) {
        values.put(key, value);
    }

    public Object getAttribute(final String key) {
        return values.getOrDefault(key, null);
    }

    public void removeAttribute(final String key) {
        values.remove(key);
    }

    public void invalidate() {
        HttpSessions.removeSession(id);
    }
}
