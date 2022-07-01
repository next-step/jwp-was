package webserver.domain.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleSession implements HttpSession {

    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    public SimpleSession() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        attributes.clear();
    }
}
