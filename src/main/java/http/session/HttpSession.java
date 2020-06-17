package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession implements Session {

    public static final String SESSION_HEADER_KEY = "SessionId";

    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    HttpSession() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    @Override
    public void invalidate() {
        this.attributes.clear();
    }
}
