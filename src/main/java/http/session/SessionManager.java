package http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {

    public static final String SESSION_NAME = "SessionId";
    private static final Map<String, HttpSession> sessionRepository = new HashMap<>();

    public static void removeSession(String id) {
        sessionRepository.remove(id);
    }

    public static String createNewSession() {
        final String newSessionId = UUID.randomUUID().toString();
        final HttpSession newSession = new HttpSession(newSessionId);
        sessionRepository.put(newSessionId, newSession);
        return newSessionId;
    }
}
