package webserver.http.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManagement {
    private static final Map<String, HttpSession> SESSION = new HashMap<>();

    public static HttpSession save() {
        HttpSession httpSession = new HttpSession(new HashMap<>());
        SESSION.put(httpSession.getId(), httpSession);
        return httpSession;
    }

    public static HttpSession getSession(String id) {
        if (!SESSION.containsKey(id)) {
            return null;
        }
        return SESSION.get(id);
    }

    public static void setSessionAttribute(HttpSession httpSession) {
        SESSION.put(httpSession.getId(), httpSession);
    }
}
