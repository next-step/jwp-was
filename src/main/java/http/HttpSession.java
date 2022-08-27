package http;

import utils.Assert;

import java.util.Objects;
import java.util.UUID;

public class HttpSession implements Session{

    private final String id;
    private final SessionAttribute sessionAttribute;

    public HttpSession(String id,
                       SessionAttribute sessionAttribute) {
        Assert.hasText(id, "id must not be null");
        Assert.notNull(sessionAttribute, "attribute must not be null");

        this.id = id;
        this.sessionAttribute = sessionAttribute;
    }

    public static HttpSession empty() {
        return new HttpSession(createID(), SessionAttribute.empty());
    }

    public boolean doesNotContainAttribute(String name) {
        return sessionAttribute.doesNotContain(name);
    }

    public void addAll(SessionAttribute sessionAttribute) {
        this.sessionAttribute.addAll(sessionAttribute);
    }
    private static String createID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        sessionAttribute.set(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return sessionAttribute.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        sessionAttribute.remove(name);
    }

    @Override
    public void invalidate() {
        sessionAttribute.invalidate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpSession that = (HttpSession) o;
        return id.equals(that.id) && sessionAttribute.equals(that.sessionAttribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionAttribute);
    }
}
