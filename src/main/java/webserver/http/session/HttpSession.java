package webserver.http.session;

import com.google.common.collect.Maps;
import utils.HttpStringType;
import utils.HttpStringUtils;

import java.util.Map;

public class HttpSession {
    private final String id;
    private Map<String, Object> attribute;

    private HttpSession(String id) {
        this.id = id;
        attribute = Maps.newHashMap();
    }

    public static HttpSession newInstance(String sessionId) {
        return new HttpSession(sessionId);
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        attribute.put(name, value);
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public boolean removeAttribute(String name) {
        attribute.remove(name);
        return !attribute.containsKey(name);
    }

    public boolean invalidate() {
        attribute = Maps.newHashMap();
        return attribute.isEmpty();
    }

    public String setCookie() {
        return HttpStringUtils.concat(HttpStringType.SESSION_ID.getType(), "=", id);
    }
}
