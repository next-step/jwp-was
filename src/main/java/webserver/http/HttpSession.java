package webserver.http;

import utils.Assert;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class HttpSession implements Session {

    private final String id;
    private final Map<String, Object> attributes;
    private final Instant createdAt = Instant.now();
    private Instant lastAccessedAt = createdAt;

    private HttpSession(String id, Map<String, Object> attributes) {
        Assert.hasText(id, "'id' must not be empty");
        Assert.notNull(attributes, "'attributes' must not be null");
        this.id = id;
        this.attributes = new ConcurrentHashMap<>(attributes);
    }

    public static HttpSession of(String id, Map<String, Object> attributes) {
        return new HttpSession(id, attributes);
    }

    public static HttpSession from(Map<String, Object> attributes) {
        return of(generateId(), attributes);
    }

    public static HttpSession empty() {
        return from(Collections.emptyMap());
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        Assert.hasText(name, "'name' for attribute must not be empty");
        Assert.notNull(value, "'value' for attribute must not be null");
        attributes.put(name, value);
    }

    @Override
    public boolean containsAttribute(String name) {
        return attributes.containsKey(name);
    }

    @Override
    public Object getAttribute(String name) {
        if (!containsAttribute(name)) {
            throw new IllegalStateException(String.format("attribute(%s) is not exists", name));
        }
        return attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        if (name == null) {
            return;
        }
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        attributes.clear();
    }

    @Override
    public void access() {
        this.lastAccessedAt = Instant.now();
    }

    @Override
    public Instant createdAt() {
        return createdAt;
    }

    @Override
    public Instant lastAccessedAt() {
        return lastAccessedAt;
    }
}
