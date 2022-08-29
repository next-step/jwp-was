package webserver.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SessionAttribute {
    private Map<String, Object> attribute;

    public SessionAttribute(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    public SessionAttribute(String key, String value) {
        this(getMap(key, value));
    }

    private static Map<String, Object> getMap(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public Map<String, Object> getAllAttribute() {
        return attribute;
    }

    public void setAttribute(String name, Object value) {
        this.attribute.put(name, value);
    }

    public void removeAttribute(String name) {
        this.attribute.remove(name);
    }

    public void invalidate() {
        attribute.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionAttribute that = (SessionAttribute) o;
        return Objects.equals(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute);
    }

    @Override
    public String toString() {
        return "SessionAttribute{" +
                "attribute=" + attribute +
                '}';
    }
}
