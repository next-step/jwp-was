package webserver.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hspark on 2019-08-14.
 */
public class HttpSession {
    private String sessionId;
    private Map<String, Object> attributes = new HashMap<>();


    public HttpSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getId() {
        return sessionId;
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }
}
