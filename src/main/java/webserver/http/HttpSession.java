package webserver.http;

import utils.Assert;

import java.time.Instant;
import java.util.UUID;

public final class HttpSession {

    private final String id;
    private final SessionAttribute attribute;
    private final Instant createdAt = Instant.now();
    private Instant lastAccessedAt = createdAt;

    private HttpSession(String id, SessionAttribute attribute) {
        Assert.hasText(id, "'id' must not be empty");
        Assert.notNull(attribute, "'attribute' must not be null");
        this.id = id;
        this.attribute = attribute;
    }

    public static HttpSession of(String id, SessionAttribute attributes) {
        return new HttpSession(id, attributes);
    }

    public static HttpSession from(SessionAttribute attributes) {
        return of(generateId(), attributes);
    }

    public static HttpSession empty() {
        return from(SessionAttribute.empty());
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public boolean isEmpty() {
        return attribute.isEmpty();
    }

    public void setAttribute(String name, Object value) {
        attribute.set(name, value);
    }

    public boolean doesNotContainAttribute(String name) {
        return attribute.doesNotContain(name);
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    public void invalidate() {
        attribute.invalidate();
    }

    public void access() {
        this.lastAccessedAt = Instant.now();
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant lastAccessedAt() {
        return lastAccessedAt;
    }

    public void addAll(SessionAttribute attribute) {
        this.attribute.addAll(attribute);
    }
}
