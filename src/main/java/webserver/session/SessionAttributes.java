package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SessionAttributes {

    private final Map<String, Object> attributes = new HashMap<>();

    public void setAttribute(final String name, final Object value) {
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
        final SessionAttributes that = (SessionAttributes) o;
        return Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }
}
