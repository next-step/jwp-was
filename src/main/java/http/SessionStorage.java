package http;

import utils.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {

    private final Map<String, HttpSession> sessions;
    private static final String DEFAULT_SESSION_COOKIE_NAME = "JSESSIONID";

    private SessionStorage(Map<String, HttpSession> sessions) {
        Assert.notNull(sessions, "sessions must not be null");
        this.sessions = new ConcurrentHashMap<>(sessions);
    }

    public static SessionStorage getInstance() {
        return Holder.instance;
    }

    public static String getDefaultSessionCookieName() {
        return DEFAULT_SESSION_COOKIE_NAME;
    }

    public Optional<HttpSession> find(String id) {

        if (!sessions.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of(sessions.get(id));
    }

    public void add(HttpSession session) {
        if(session == null) {
            return;
        }

        sessions.put(session.getId(), session);
    }

    public void remove(HttpSession session) {
        if(session == null) {
            return;
        }

        sessions.remove(session.getId());
    }

    private static class Holder {
        private static final SessionStorage instance = new SessionStorage(Collections.emptyMap());
    }
}
