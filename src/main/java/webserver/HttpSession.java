package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private static final String SESSION_ID = "JSESSIONID";
    private static final String KEY_VALUE_DELIMITER = "=";


    private String id;
    private Map<String, Object> attributes = new HashMap<>();


    public HttpSession() {
        this.id = UUID.randomUUID().toString();
    }
    public HttpSession(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void invalidate() {
        Map<String, HttpSession> sessionMap = HttpSessionStorage.getSessionMap();
        sessionMap.remove(id);
        attributes.clear();
    }

    public String setSessionId() {
        return SESSION_ID + KEY_VALUE_DELIMITER + this.id;
    }
}
