package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession implements Session {
    private String id;

    private Map<String, Object> attribute;

    public HttpSession(String id) {
        this.id = id;
        this.attribute = new HashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        attribute.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    @Override
    public void invalidate() {
        attribute.clear();
    }
}
