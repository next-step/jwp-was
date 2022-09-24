package webserver.http.domain.session;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    private static final ConcurrentHashMap<SessionId, HttpSession> storage = new ConcurrentHashMap<>();

    public static HttpSession getSession(SessionId sessionId) {
        if (!storage.containsKey(sessionId)) {
            throw new NoSuchElementException();
        }
        return storage.get(sessionId);
    }

    public static void add(HttpSession httpSession) {
        storage.put(httpSession.sessionId(), httpSession);
    }

    public static void remove(SessionId sessionId) {
        storage.remove(sessionId);
    }
}
