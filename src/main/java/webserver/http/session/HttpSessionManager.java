package webserver.http.session;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpStringUtils;
import webserver.http.request.HttpRequestHeader;

import java.util.Map;
import java.util.UUID;

public class HttpSessionManager {
    private static final Logger log = LoggerFactory.getLogger(HttpSessionManager.class);

    private static Map<String, HttpSession> sessions;

    static {
        sessions = Maps.newHashMap();
    }

    public static HttpSession getSession(HttpRequestHeader requestHeader) {
        String values = requestHeader.findByKey("Cookie");
        String sessionId = HttpStringUtils.extractSessionId(values);

        return getSession(sessionId);
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

    public static HttpSession login(Object attribute) {
        HttpSession newHttpSession = newSession();
        newHttpSession.setAttribute("user", attribute);

        return newHttpSession;
    }

    private static void remove(HttpRequestHeader requestHeader) {
        HttpSession httpSession = getSession(requestHeader);
        sessions.remove(httpSession.getId());

        log.debug("remove session : {}, session_count : {}", httpSession.getId(), sessions.size());
    }

    public static boolean remove(String id) {
        sessions.remove(id);
        return !sessions.containsKey(id);
    }
}
