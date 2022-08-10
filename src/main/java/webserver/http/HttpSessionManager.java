package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManager {
    private final static Map<String, HttpSession> HTTP_SESSIONS = new HashMap<>();

    public static HttpSession getHttpSession(HttpRequest httpRequest) {
        final String sessionId = httpRequest.getSessionCookie();
        final HttpSession httpSession = HTTP_SESSIONS.get(sessionId);
        if (httpSession != null) {
            return httpSession;
        }
        return newHttpSession();
    }

    private static HttpSession newHttpSession() {
        final HttpSession httpSession = new HttpSession();
        HTTP_SESSIONS.put(httpSession.getId(), httpSession);
        return httpSession;
    }
}
