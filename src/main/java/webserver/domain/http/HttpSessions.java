package webserver.domain.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSessions {

    private static final Map<String, HttpSession> httpSessionMap = new HashMap<>();

    public static HttpSession create() {
        return getNewSession();

    }

    public static HttpSession get(String id) {
        HttpSession session = httpSessionMap.get(id);

        if (Objects.isNull(session)) {
            return getNewSession();
        }

        return session;
    }

    private static HttpSession getNewSession() {
        HttpSession simpleSession = new SimpleSession();
        httpSessionMap.put(simpleSession.getId(), simpleSession);

        return simpleSession;
    }
}
