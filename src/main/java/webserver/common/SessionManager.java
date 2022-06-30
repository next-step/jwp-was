package webserver.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import webserver.common.exception.IllegalCookieKeyException;
import webserver.common.exception.IllegalSessionIdException;

public class SessionManager {

    private static final String SESSION_COOKIE_KEY = "sessionId";

    private SessionManager() {}

    private static final Map<String, HttpSession> container = new HashMap<>();

    public static void add(HttpSession httpSession) {
        container.put(httpSession.getId(), httpSession);
    }

    public static HttpSession createSession() {
        HttpSession httpSession = new HttpSessionImpl();
        add(httpSession);
        return httpSession;
    }

    public static HttpSession get(String sessionId) {
        return Optional.ofNullable(container.get(sessionId))
                .orElseThrow(() -> new IllegalSessionIdException(sessionId));
    }

    public static HttpSession getSession(HttpCookie httpCookie) {
        try {
            String sessionId = httpCookie.get(SESSION_COOKIE_KEY);
            return get(sessionId);
        } catch (IllegalCookieKeyException | IllegalSessionIdException e) {
            HttpSession httpSession = createSession();
            httpCookie.put(SESSION_COOKIE_KEY, httpSession.getId());
            return httpSession;
        }
    }

    public static void invalidate(String sessionId) {
        get(sessionId).invalidate();
        container.remove(sessionId);
    }
}
