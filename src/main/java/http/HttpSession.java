package http;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HttpSession {
    private static final String SESSION_ID = "JSESSONID";

    private Map<String, Object> session;

    public HttpSession(String sessionId) {
        this.session = new HashMap<>();
        setAttribute(SESSION_ID, sessionId);
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
