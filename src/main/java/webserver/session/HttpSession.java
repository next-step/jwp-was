package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private Map<String, Object> session = new HashMap<>();

    public HttpSession() {
        session.put("uuid", UUID.randomUUID());
        Sessions.addSession(this);
    }

    public String getId() {
        return session.get("uuid").toString();
    }

    public void setAttribute(String name, Object value) {
        session.put(name, value);
    }

    public Object getAttribute(String name) {
        return session.get(name);
    }

    public void invalidate() {
        session.clear();
    }

    public int getSize() {
        return session.size();
    }
}
