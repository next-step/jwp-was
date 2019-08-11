package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {

    private final Map<String, HttpSession> httpSessions = new ConcurrentHashMap<>();

    public HttpSession getSession(String sessionId) {
        if (StringUtils.isNotEmpty(sessionId) && httpSessions.containsKey(sessionId)) {
            return httpSessions.get(sessionId);
        }
        return createSession();
    }

    public HttpSession createSession() {
        String id = UUID.randomUUID().toString();
        return putHttpSession(new HttpSession(id));
    }

    private HttpSession putHttpSession(HttpSession httpSession) {
        httpSessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }

}
