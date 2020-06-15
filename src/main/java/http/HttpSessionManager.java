package http;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpSessionManager {
    private final Map<String, HttpSession> httpSessionMap;

    public HttpSessionManager() {
        this.httpSessionMap = Maps.newHashMap();
    }

    public void addSession(HttpSession session) {
        httpSessionMap.put(session.getId(), session);
    }

    public void findSession(HttpSession httpSession) {
        httpSessionMap.get(httpSession.getId());
    }

    public void removeSession(HttpSession httpSession) {
        httpSessionMap.remove(httpSession.getId());
    }
}
