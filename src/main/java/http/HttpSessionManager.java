package http;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author KingCjy
 */
public class HttpSessionManager {

    private static Map<String, HttpSession> httpSessions = new LinkedHashMap<>();

    public static HttpSession getSession(String id) {
        return httpSessions.get(id);
    }

    public static void addSession(HttpSession httpSession) {
        httpSessions.put(httpSession.getId(), httpSession);
    }
}
