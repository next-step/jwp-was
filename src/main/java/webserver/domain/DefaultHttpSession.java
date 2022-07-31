package webserver.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultHttpSession implements HttpSession {

    private final String id;

    private SessionManager manager;
    private final Map<String, Object> store = new HashMap<>();

    public DefaultHttpSession() {
        this(UUID.randomUUID().toString(), Collections.emptyMap());
    }

    public DefaultHttpSession(String id, Map<String, Object> store) {
        this.id = id;
        this.store.putAll(store);
    }

    public static HttpSession newInstance(SessionManager manager) {
        DefaultHttpSession session = new DefaultHttpSession();
        session.manager = manager;
        manager.addSession(session);

        return session;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        store.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return store.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        store.remove(name);
    }

    @Override
    public void invalidate() {
        store.clear();
        manager.removeSession(getId());
        manager = null;
    }

    @Override
    public Cookie getCookie() {
        return new Cookie(SESSION_COOKIE_NAME, getId());
    }
}
