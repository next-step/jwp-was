package http;

import utils.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SessionAttribute {
    private final Map<String, Object> attributes;

    public SessionAttribute(Map<String, Object> attributes) {
        Assert.notNull(attributes, "attributes must not be null");
        this.attributes = new ConcurrentHashMap<>(attributes);
    }

    public static SessionAttribute empty() {
        return new SessionAttribute(Collections.emptyMap());
    }

    Object get(String name) {
        if (!attributes.containsKey(name)) {
            throw new IllegalArgumentException(String.format("attribute(%s) is not exist", name));
        }

        return attributes.get(name);
    }

    void set(String name, Object value) {
        attributes.put(name, value);
    }

    void remove(String name) {
        if (name == null || !attributes.containsKey(name)) {
            return;
        }

        attributes.remove(name);
    }

    boolean doesNotContain(String name) {
        return !attributes.containsKey(name);
    }

    void invalidate() {
        attributes.clear();
    }

    public boolean isEmpty() {
        return attributes.isEmpty();
    }

    public void addAll(SessionAttribute sessionAttribute) {
        attributes.putAll(sessionAttribute.attributes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionAttribute that = (SessionAttribute) o;
        return attributes.equals(that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }
}
