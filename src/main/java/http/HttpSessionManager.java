package http;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author KingCjy
 */
public class HttpSessionManager {

    private Map<String, HttpSession> httpSessions = new LinkedHashMap<>();

    public HttpSession getSession(String id) {
        return httpSessions.get(id);
    }

    public void addSession(HttpSession httpSession) {
        httpSessions.put(httpSession.getId(), httpSession);
    }

    public HttpSession createSession() {
        HttpSession httpSession = new HttpSession();
        this.addSession(httpSession);

        return httpSession;
    }

    public HttpSession createSession(String id) {
        HttpSession httpSession = new HttpSession(id);
        this.addSession(httpSession);

        return httpSession;
    }

}
