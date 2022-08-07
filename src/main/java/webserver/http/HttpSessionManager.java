package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManager {
    private final static Map<String, HttpSession> HTTP_SESSIONS = new HashMap<>();
    public static final String SESSION_ID = "sessionId";

    public static HttpSession getHttpSession(HttpRequest httpRequest) {
        final String sessionId = httpRequest.getCookie(SESSION_ID);
        final HttpSession httpSession = HTTP_SESSIONS.get(sessionId);
        if (httpSession != null) {
            return httpSession;
        }
        return newHttpSession();
    }

    public static void setSessionIdToCookie(HttpResponse httpResponse, HttpSession httpSession) {
        httpResponse.setCookie(SESSION_ID, httpSession.getId());
    }

    private static HttpSession newHttpSession() {
        final HttpSession httpSession = new HttpSession();
        HTTP_SESSIONS.put(httpSession.getId(), httpSession);
        return httpSession;
    }
}
