package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SessionManager {

    public static final String SESSION_NAME = "SessionId";
    private static final Map<String, HttpSession> sessionRepository = new HashMap<>();

    public static void removeSession(String id) {
        sessionRepository.remove(id);
    }

    public static String createNewSession() {
        final HttpSession session = createSession(UUID.randomUUID().toString());
        return session.getId();
    }

    private static HttpSession createSession(String id) {
        final HttpSession newSession = new HttpSession(id);
        sessionRepository.put(id, newSession);
        return newSession;
    }

    public static HttpSession getSession(String id) {
        final Optional<HttpSession> maybeSession = Optional.ofNullable(sessionRepository.get(id));
        return maybeSession.orElseGet(() -> SessionManager.createSession(id));
    }
}
