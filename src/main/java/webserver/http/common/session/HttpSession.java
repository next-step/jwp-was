package webserver.http.common.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : yusik
 * @date : 2019-08-12
 */
public class HttpSession {

    private Map<String, Object> attributes = new ConcurrentHashMap<>();
    private String id;

    public HttpSession() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void invalidate() {
        attributes.clear();
    }

}
