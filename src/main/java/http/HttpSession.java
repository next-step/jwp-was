package http;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class HttpSession {
    private static final String SESSION_ID = "JSESSONID";

    private Map<String, Object> session;

    public HttpSession() {
        this.session = new HashMap<>();
    }

    public void createSessionId() {
        setAttribute(SESSION_ID, UUID.randomUUID().toString());
    }

    public boolean isEmpty() {
        return session.isEmpty();
    }

    public String getId() {
        return (String) session.get(SESSION_ID);
    }

    public void setAttribute(String key, Object value) {
        session.put(key, value);
    }

    public Object getAttribute(String key) {
        return session.get(key);
    }

    public void removeAttribute(String key) {
        session.remove(key);
    }

    public void invalidate() {
        session.clear();
    }
}
