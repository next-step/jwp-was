package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    public static final String COOKIE_KEY = "sessionId";

    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    private HttpSession(String id) {
        this.id = id;
    }

    public static HttpSession from(String id) {
        return new HttpSession(id);
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);

    }

    public Object getAttribute(String name) {
        return attributes.getOrDefault(name, null);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }
}
