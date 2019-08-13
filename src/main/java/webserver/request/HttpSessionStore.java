package webserver.request;

import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by hspark on 2019-08-14.
 */
public class HttpSessionStore {
    private static Map<String, HttpSession> sessionMap = Collections.synchronizedMap(new HashMap<>());

    public static synchronized HttpSession getSession(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return createSession();
        }
        return sessionMap.computeIfAbsent(sessionId, HttpSession::new);
    }

    private static HttpSession createSession() {
        String sessionId = UUID.randomUUID().toString();
        HttpSession httpSession = new HttpSession(sessionId);
        sessionMap.put(sessionId, httpSession);
        return httpSession;
    }
}
