package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private static final Map<String, Object> attributes = new HashMap<>();

    private HttpSessionId id;

    public HttpSession(HttpSessionId id) {
        this.id = id;
    }

    public String getId() {
        return id.getId();
    }

    public void setAttribute(String attributeName, Object attributeValue) {
        attributes.put(attributeName, attributeValue);
    }

    public Object getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    public void removeAttribute(String attributeName) {
        attributes.remove(attributeName);
    }

    public void invalidate() {
        HttpSessionStore.remove(id);
    }
}
