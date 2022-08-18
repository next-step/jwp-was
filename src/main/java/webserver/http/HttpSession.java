package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final Map<String, Object> map = new HashMap<>();
    private final String id;

    private HttpSession(String id) {
        this.id = id;
    }

    public static HttpSession of(String id) {
        return new HttpSession(id);
    }

    public String getId() {
        return this.id;
    }

    public void setAttribute(String name, Object value) {
        this.map.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.map.get(name);
    }

    public void removeAttribute(String name) {
        map.remove(name);
    }

    public void invalidate(String id) {
        HttpSessions.remove(id);
    }

}
