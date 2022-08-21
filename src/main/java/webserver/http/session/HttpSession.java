package webserver.http.session;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpSession {
    private String id;
    private SessionAttribute attribute;

    public HttpSession(String id, SessionAttribute attribute) {
        this.id = id;
        this.attribute = attribute;
    }

    public HttpSession(Map<String, Object> attribute) {
        this(generateSessionId(), new SessionAttribute(attribute));
    }

    private static String generateSessionId() {
        return String.valueOf(UUID.randomUUID());
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        attribute.setAttribute(name, value);
    }

    public Object getAttribute(String name) {
        return attribute.getAttribute(name);
    }

    public void removeAttribute(String name) {
        attribute.removeAttribute(name);
    }

    public void invalidate() {
        attribute.invalidate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpSession that = (HttpSession) o;
        return Objects.equals(id, that.id) && Objects.equals(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attribute);
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "id='" + id + '\'' +
                ", attribute=" + attribute +
                '}';
    }
}
