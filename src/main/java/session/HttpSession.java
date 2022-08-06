package session;

import java.util.Objects;
import java.util.UUID;

public class HttpSession {
    private final String id;
    private SessionAttribute sessionAttribute;

    public HttpSession(String id, SessionAttribute sessionAttribute) {
        this.id = id;
        this.sessionAttribute = sessionAttribute;
    }

    public static HttpSession from(SessionAttribute sessionAttribute) {
        return new HttpSession(generateId(), sessionAttribute);
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String key, String value) {
        sessionAttribute.setAttributes(key, value);
    }

    public Object getAttribute(String key) {
        return sessionAttribute.getAttribute(key);
    }

    public void removeAttribute(String id) {
        sessionAttribute.removeAttribute(id);
    }

    public void invalidate() {
        sessionAttribute.invalidate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpSession session = (HttpSession) o;
        return Objects.equals(id, session.id) && Objects.equals(sessionAttribute,
            session.sessionAttribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionAttribute);
    }
}
