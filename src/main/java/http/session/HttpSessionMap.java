package http.session;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpSessionMap {

    private static final ConcurrentHashMap<String, HttpSession> SESSION_MAP = new ConcurrentHashMap<>();

    public static HttpSession createSessionIfAbsent(String id) {
        HttpSession session = getSession(id);
        return session != null ? session : createSession();
    }

    public static HttpSession createSession() {
        HttpSession httpSession = new HttpSession();
        SESSION_MAP.put(httpSession.getId(), httpSession);

        return httpSession;
    }

    public static HttpSession getSession(String id) {
        if (id == null) {
            return null;
        }

        return SESSION_MAP.get(id);
    }
}
