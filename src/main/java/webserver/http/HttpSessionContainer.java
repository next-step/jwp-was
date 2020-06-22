package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionContainer {

    private static Map<String, HttpSession> httpSessions = new HashMap<>();

    public static HttpSession get(String sessionId) {
        return httpSessions.get(sessionId);
    }

    public static HttpSession create() {
        HttpSession httpSession = HttpSessionFactory.create();
        httpSessions.put(httpSession.getId(), httpSession);
        return httpSession;
    }
}
