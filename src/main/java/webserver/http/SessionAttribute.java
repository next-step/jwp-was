package webserver.http;

import utils.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SessionAttribute {

    private final Map<String, Object> attributes;

    private SessionAttribute(Map<String, Object> attributes) {
        Assert.notNull(attributes, "'attributes' must not be null");
        this.attributes = new ConcurrentHashMap<>(attributes);
    }

    public static SessionAttribute from(Map<String, Object> attributes) {
        return new SessionAttribute(attributes);
    }

    public static SessionAttribute empty() {
        return from(Collections.emptyMap());
    }

    public boolean isEmpty() {
        return attributes.isEmpty();
    }

    boolean doesNotContain(String name) {
        return !attributes.containsKey(name);
    }

    void invalidate() {
        attributes.clear();
    }

    Object get(String name) {
        if (doesNotContain(name)) {
            throw new IllegalStateException(String.format("attribute(%s) is not exists", name));
        }
        return attributes.get(name);
    }

    void set(String name, Object value) {
        Assert.hasText(name, "'name' must not be empty");
        Assert.notNull(value, "'value' must not be null");
        attributes.put(name, value);
    }

    void remove(String name) {
        if (name == null) {
            return;
        }
        attributes.remove(name);
    }

    void addAll(SessionAttribute attribute) {
        attributes.putAll(attribute.attributes);
    }
}
