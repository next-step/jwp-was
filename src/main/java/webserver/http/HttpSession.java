package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    public static final String SESSION_ID_NAME = "JSESSIONID";
    private String id;
    private final Map<String, Object> values = new HashMap<>();

    public HttpSession(String id) {
        this.id = id;
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
        values.remove(name);
    }

    public void invalidate() {
        HttpSessionStorage.remove(id);
    }
}
