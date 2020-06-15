package http.session;

import java.util.Objects;

public class HttpSession {
    private final String sessionId;
    private final Attribute attribute;

    public HttpSession(final String sessionId) {
        this.sessionId = sessionId;
        this.attribute = new Attribute();
    }

    public boolean hasSession(String sessionId) {
        return this.sessionId.equals(sessionId);
    }

    public String getId() {
        return this.sessionId;
    }

    public void setAttribute(String name, Object value) {
        this.attribute.setAttribute(name, value);
    }

    public Object getAttribute(String name) {
        return this.attribute.getAttribute(name);
    }

    public void removeAttribute(String name) {
        this.attribute.removeAttribute(name);
    }

    public void invalidate() {
        this.attribute.invalidate();
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
