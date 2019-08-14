package webserver.http.common.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : yusik
 * @date : 2019-08-13
 */
public class SessionManager {

    public static final String SESSION_HEADER_NAME = "JSESSIONID";

    private static Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

    private static HttpSession createSession() {
        HttpSession httpSession = new HttpSession();
        sessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public static HttpSession getSession(String sessionId) {

        if (sessionId == null) {
            return createSession();
        }

        HttpSession httpSession = sessions.get(sessionId);
        if (httpSession == null) {
            return createSession();
        }

        return httpSession;
    }

    public static HttpSession remove(String sessionId) {
        return sessions.remove(sessionId);
    }

}
