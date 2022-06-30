package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    public HttpSession(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public Object getAttributeOrNull(String name) {
        return this.attributes.getOrDefault(name, null);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public void invalidate() {
        this.attributes.clear();
        HttpSessionManager.remove(this);
    }
}
