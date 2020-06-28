package http;

import java.util.HashMap;
import java.util.Map;


public class HttpSession implements Session {
    Map<String, Object> session = new HashMap<>();
    private final String id;

    public HttpSession(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return session.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        session.remove(name);
    }

    @Override
    public void invalidate() {
        session.clear();
    }
}
