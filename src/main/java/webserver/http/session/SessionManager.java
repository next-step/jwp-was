package webserver.http.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    public static final String SESSION_KEY = "JSESSIONID";
    private static final Map<SessionId, HttpSession> sessionStore = new ConcurrentHashMap<>();

    public static HttpSession getSession(SessionId sessionId) {
        HttpSession httpSession = sessionStore.get(sessionId);

        if (httpSession == null) {
            httpSession = new HttpSession(sessionId);
            addSession(httpSession);
            return httpSession;
        }
        return httpSession;
    }

    public static void addSession(HttpSession httpSession) {
        SessionId sessionId = new SessionId(httpSession.getId());
        sessionStore.put(sessionId, httpSession);
    }

    public static void removeSession(SessionId sessionId) {
        sessionStore.remove(sessionId);
    }

    public static void clearSessions() {
        sessionStore.clear();
    }
}
