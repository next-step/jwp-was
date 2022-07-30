package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSession {

    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    public HttpSession(final SessionIdGenerator sessionIdGenerator) {
        this.id = sessionIdGenerator.generate();
    }

    public void setAttribute(final String name, final String value) {
        attributes.put(name, value);
    }

    public Object getAttribute(final String name) {
        return attributes.get(name);
    }

    public void removeAttribute(final String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
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
