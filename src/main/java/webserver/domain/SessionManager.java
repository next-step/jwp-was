package webserver.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final SessionManager instance = new SessionManager();

    private final Map<String, HttpSession> sessionStore = new ConcurrentHashMap<>();

    private SessionManager(){}

    public static SessionManager getInstance() {
        return instance;
    }

    public HttpSession createSession() {
        HttpSession session = DefaultHttpSession.newInstance(this);
        sessionStore.put(session.getId(), session);

        return session;
    }

    public void removeSession(String sessionId) {
        sessionStore.remove(sessionId);
    }

    public HttpSession findBySessionId(String sessionId) {
        return sessionStore.get(sessionId);
    }

    public void addSession(HttpSession session) {
        sessionStore.put(session.getId(), session);
    }

    public int size() {
        return sessionStore.size();
    }

    public void clear() {
        sessionStore.clear();
    }
}
