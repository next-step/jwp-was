package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSession {

    private final Map<String, Object> attributes;
    private final String id;

    public HttpSession(String id) {
        this.id = id;
        this.attributes = new HashMap<>();
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpSession that = (HttpSession) o;
        return Objects.equals(attributes, that.attributes) &&
            Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes, id);
    }
}
