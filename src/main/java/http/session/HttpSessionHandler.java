package http.session;

import java.util.ArrayList;
import java.util.List;

public class HttpSessionHandler {
    private static List<HttpSession> sessions;
    public HttpSessionHandler() {
        sessions = new ArrayList<>();
    }

    public static HttpSession getSession(String sessionId) {
        return sessions.stream()
                .filter(httpSession -> httpSession.hasSession(sessionId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("not found session"));
    }

    public static void applySession(HttpSession httpSession) {
        sessions.add(httpSession);
    }
}
