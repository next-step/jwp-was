package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSession {
    private final String sessionId;
    private final Map<String, Object> attribute;

    public HttpSession(final String sessionId) {
        this.sessionId = sessionId;
        this.attribute = new HashMap<>();
    }

    public String getId() {
        return this.sessionId;
    }

    public void setAttribute(String name, Object value) {
        this.attribute.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.attribute.get(name);
    }

    public void removeAttribute(String name) {
        this.attribute.remove(name);
    }

    public void invalidate() {
        this.attribute.clear();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HttpSession that = (HttpSession) o;
        return Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}
