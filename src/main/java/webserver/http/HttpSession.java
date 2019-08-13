package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

    public static final String DEFAULT_SESSION_KEY = "JSESSIONID";

    private String id;

    private Map<String, Object> session;

    public HttpSession(String id) {
        this.session = new HashMap<>();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    public Object getAttribute(String name) {
        return session.get(name);
    }

    public void removeAttribute(String name) {
        session.remove(name);
    }

    public void invalidate() {
        HttpSessionContext.remove(id);
    }
}
