package webserver.http.session;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;


public class LocalSessionStorage {

    private static ConcurrentHashMap<SessionId, HttpSession> storage = new ConcurrentHashMap();

    public static HttpSession getSession(SessionId sessionId) {
        if (!storage.containsKey(sessionId)) {
            throw new NoSuchElementException();
        }
        return storage.get(sessionId);
    }

    public static void addSession(HttpSession httpSession) {
        storage.put(httpSession.getSessionId(), httpSession);
    }

    public static void removeSession(SessionId sessionId) {
        storage.remove(sessionId);
    }

    public static void updateSession(HttpSession httpSession) {
        storage.put(httpSession.getSessionId(), httpSession);
    }
}
