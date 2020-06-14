package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Attribute {
    private final Map<String, Object> attribute;

    public Attribute() {
        this.attribute = new HashMap<>();
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
        final Attribute attribute1 = (Attribute) o;
        return Objects.equals(attribute, attribute1.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute);
    }
}
