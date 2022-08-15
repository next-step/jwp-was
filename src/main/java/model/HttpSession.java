package model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final UUID uuid;
    private final Map<String, Object> attribute;

    public HttpSession(UUID uuid) {
        this.uuid = uuid;
        this.attribute = new HashMap<>();
    }

    public String getId() {
        return uuid.toString();
    }

    public void setAttribute(String name, Object object) {
        attribute.put(name, object);
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    public void invalidate() {
        attribute.clear();
    }
}
