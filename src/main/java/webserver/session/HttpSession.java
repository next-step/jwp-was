package webserver.session;

import com.google.common.collect.Maps;

import java.util.Map;

public class HttpSession {

    public static final String SESSION_ID = "sessionId";

    private Map<String, Object> session = Maps.newHashMap();

    public void setAttribute(String key, Object value) {
        session.put(key, value);
    }

    public Object getAttribute(String key) {
        return session.get(key);
    }

    public void removeAttribute(String key) {
        session.remove(key);
    }

    public void invalidate() {
        session.clear();
    }

    public int size() {
        return session.size();
    }
}
