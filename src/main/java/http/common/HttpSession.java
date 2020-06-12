package http.common;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

public class HttpSession {
    @Getter
    private final String id;
    private final Map<String, Object> attributes = Maps.newHashMap();

    public HttpSession() {
        this(UUID.randomUUID().toString());
    }

    public HttpSession(String id) {
        this.id = id;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }
}
