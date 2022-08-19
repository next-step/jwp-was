package webserver.session;

import exception.Assert;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {

    private String id;
    private Map<String, Object> attributes = new ConcurrentHashMap<>();

    public HttpSession(String id) {
        Assert.hasText(id, "id는 빈값이어선 안됩니다.");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        Assert.hasText(name, "키값이 null이어선 안됩니다.");
        Assert.notNull(value, "value 값이 null이어선 안됩니다.");
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
        HttpSessionStorage.remove(id);
    }

    public boolean contains(String key) {
        return attributes.containsKey(key);
    }
}
