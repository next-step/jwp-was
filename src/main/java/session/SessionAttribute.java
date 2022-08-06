package session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SessionAttribute {
    private Map<String, Object> attributes;

    public SessionAttribute() {
        this.attributes = new HashMap<>();
    }

    public void setAttributes(String id, Object value) {
        attributes.put(id, value);
    }

    public Object getAttribute(String id) {
        return attributes.get(id);
    }

    public void removeAttribute(String id) {
        if(id == null) return;
        attributes.remove(id);
    }

    public void invalidate() {
        attributes.clear();
    }

    public boolean isEmpty() {
        return attributes.isEmpty();
    }

    public static SessionAttribute empty() {
        return new SessionAttribute();
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionAttribute that = (SessionAttribute) o;
        return Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }
}
