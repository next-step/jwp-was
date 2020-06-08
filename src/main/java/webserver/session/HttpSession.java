package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private static final String ID = "id";

    private Map<String, Object> session = new HashMap<>();

    public HttpSession() {
        session.put(ID, UUID.randomUUID());
        Sessions.addSession(this);
    }

    public String getId() {
        return session.get(ID).toString();
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
