package webserver.session;

import java.util.Objects;

public class HttpSession {

    private final SessionId id;
    private final SessionAttributes attributes = new SessionAttributes();

    public HttpSession(final SessionIdGenerator sessionIdGenerator) {
        this.id = new SessionId(sessionIdGenerator);
    }

    public String getId() {
        return id.getId();
    }

    public void setAttribute(final String name, final Object value) {
        attributes.setAttribute(name, value);
    }

    public Object getAttribute(final String name) {
        return attributes.getAttribute(name);
    }

    public void removeAttribute(final String name) {
        attributes.removeAttribute(name);
    }

    public void invalidate() {
        attributes.invalidate();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HttpSession that = (HttpSession) o;
        return Objects.equals(id, that.id) && Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attributes);
    }
}
