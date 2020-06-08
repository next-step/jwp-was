package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private Map<String, Object> session = new HashMap<>();

    public HttpSession() {
        session.put("uuid", UUID.randomUUID());
    }

    public UUID getId() {
        return (UUID) session.get("uuid");
    }

    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    public Object getAttribute(String name) {
        return session.get(name);
    }
}
