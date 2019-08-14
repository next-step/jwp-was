package webserver.http.common.session;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author : yusik
 * @date : 2019-08-13
 */
public class SessionManager {

    public static final String SESSION_HEADER_NAME = "JSESSIONID";

    private final ConcurrentMap<String, HttpSession> sessions;

    public SessionManager() {
        sessions = new ConcurrentHashMap<>();
    }

    public HttpSession createSessionIfAbsent(String sessionId) {
        HttpSession httpSession = new HttpSession();
        String id = Optional.ofNullable(sessionId).orElse(httpSession.getId());
        return Optional.ofNullable(sessions.putIfAbsent(id, httpSession)).orElse(httpSession);
    }

    public HttpSession remove(String sessionId) {
        return sessions.remove(sessionId);
    }

}
