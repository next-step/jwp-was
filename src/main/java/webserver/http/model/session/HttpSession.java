package webserver.http.model.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private final String id;
    private SessionAttribute attributes;

    public HttpSession() {
        this(UUID.randomUUID().toString(), new HashMap<>());
    }

    public HttpSession(String id) {
        this(id, new HashMap<>());
    }

    public HttpSession(String id, Map<String, Object> attributes) {
        this(id, new SessionAttribute(attributes));
    }

    public HttpSession(String id, SessionAttribute attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        attributes.setAttribute(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.getAttribute(name);
    }

    public void removeAttribute(String name) {
        attributes.removeAttribute(name);
    }

    public void invalidate() {
        attributes.invalidate();
    }

    Map<String, Object> getAttributes() {
        return attributes.getAttributes();
    }
}
