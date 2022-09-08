package model.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private String id;
    private Map<String, Object> values = new HashMap<>();

    public HttpSession() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        values.put(name, value);
    }

    public Object getAttribute(String name) {
        return values.get(name);
    }

    public void removeAttribute(String name) {
        if (!values.containsKey(name)) {
            throw new IllegalArgumentException("존재하지 않는 세션입니다.");
        }
        values.remove(name);
    }

    public void invalidate() {
        values.clear();
    }
}
