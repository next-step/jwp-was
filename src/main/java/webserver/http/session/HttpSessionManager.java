package webserver.http.session;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {
    private static final Logger log = LoggerFactory.getLogger(HttpSessionManager.class);

    private static Map<String, HttpSession> sessions;

    static {
        sessions = Maps.newHashMap();
    }

    public static HttpSession getSession(String sessionId) {
        if (sessionId == null) {
            return newSession();
        }

        if (!sessions.containsKey(sessionId)) {
            return newSession();
        }

        return sessions.get(sessionId);
    }

    private static HttpSession newSession() {
        HttpSession httpSession = HttpSession.newInstance(UUID.randomUUID().toString());
        sessions.put(httpSession.getId(), httpSession);

        log.debug("create new session : {}, session_count : {}", httpSession.getId(), sessions.size());
        return sessions.get(httpSession.getId());
    }

    public static boolean isRegistered(String id) {
        return sessions.containsKey(id);
    }

    public static boolean remove(String id) {
        sessions.remove(id);
        return !sessions.containsKey(id);
    }
}
