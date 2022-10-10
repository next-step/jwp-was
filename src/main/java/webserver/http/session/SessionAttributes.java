package webserver.http.session;

import exception.NotFoundAttributeException;

import java.util.HashMap;
import java.util.Map;

public class SessionAttributes {

    private final Map<String, Object> attributes = new HashMap<>();

    public Object getAttribute(String name) {
        if (!attributes.containsKey(name)) {
            throw new NotFoundAttributeException(name);
        }
        return attributes.get(name);
    }

    public int getAttributesSize() {
        return attributes.size();
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        if (!attributes.containsKey(name)) {
            throw new NotFoundAttributeException(name);
        }
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }
}
