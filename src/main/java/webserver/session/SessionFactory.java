package webserver.session;

import com.google.common.collect.Maps;
import java.util.Map;

public class SessionFactory {
    private Map<String, HttpSession> sessionMap = Maps.newHashMap();

    public void makeSession(String id, HttpSession session) {
        sessionMap.put(id, session);
    }

    public HttpSession getSession(String id) {
        return sessionMap.get(id);
    }
}
