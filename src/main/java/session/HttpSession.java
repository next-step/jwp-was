package session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession implements Session {

    private final UUID id;

    private final Map<String, Object> attributeMap;

    public HttpSession() {
        this.id = UUID.randomUUID();
        this.attributeMap = new HashMap<>();
    }

    @Override
    public String getId() {
        return id.toString();
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.attributeMap.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return this.attributeMap.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        this.attributeMap.remove(name);
    }

    @Override
    public void invalidate() {
        this.attributeMap.clear();
    }
}
