package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class SessionAttribute {
    private final Map<String, Object> attribute;

    public SessionAttribute() {
        this.attribute = new HashMap<>();
    }

    void setAttribute(String name, Object value) {
        attribute.put(name, value);
    }

    Object getAttribute(String name) {
        return attribute.get(name);
    }

    void remove(String name) {
        attribute.remove(name);
    }

    void clear() {
        attribute.clear();
    }
}
