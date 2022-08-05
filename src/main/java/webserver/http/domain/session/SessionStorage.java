package webserver.http.domain.session;

import webserver.http.domain.cookie.Cookie;
import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;

import java.util.Map;

public class SessionStorage {
    private static final String COOKIE_NAME_OF_SESSION_ID = "JWP_SID";

    private final Map<String, Session> sessions;

    private final SessionIdGenerator sessionIdGenerator;

    public SessionStorage(Map<String, Session> sessions, SessionIdGenerator sessionIdGenerator) {
        this.sessions = sessions;
        this.sessionIdGenerator = sessionIdGenerator;
    }

    public void setupBeforeProcess(Request request) {
        String sessionId = request.getCookie(COOKIE_NAME_OF_SESSION_ID)
                .map(Cookie::getValue)
                .orElseGet(sessionIdGenerator::generate);

        Session savedSession = saveIfAbsentAndGetSession(sessionId);
        SessionContextHolder.saveSession(savedSession);
    }

    private Session saveIfAbsentAndGetSession(String sessionId) {
        sessions.putIfAbsent(sessionId, Session.from(sessionId));
        return sessions.get(sessionId);
    }

    public void teardownAfterProcess(Response response) {
        Session currentSession = SessionContextHolder.getCurrentSession();
        String sessionId = currentSession.getId();

        if (currentSession.isEmptyAttributes()) {
            sessions.remove(sessionId);
            return;
        }

        response.addCookie(Cookie.of(COOKIE_NAME_OF_SESSION_ID, sessionId));
    }
}
