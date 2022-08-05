package webserver.http.domain.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Session {

    private final String sessionId;
    private final Map<String, Object> attributes;

    public Session(String sessionId, Map<String, Object> attributes) {
        this.sessionId = sessionId;
        this.attributes = attributes;
    }

    public static Session from(String sessionId) {
        return new Session(sessionId, new ConcurrentHashMap<>());
    }

    public String getId() {
        return sessionId;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }

    public boolean isEmptyAttributes() {
        return attributes.isEmpty();
    }
}
