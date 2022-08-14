package webserver.http.model.session;

import java.util.Map;

public class SessionAttribute {
    private Map<String, Object> attributes;

    public SessionAttribute(Map<String, Object> attributes) {
        this.attributes = attributes;
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

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
