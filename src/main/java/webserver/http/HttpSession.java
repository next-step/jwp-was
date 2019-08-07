package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {


    private final HttpSessionManager manager;
    private final String id;
    private final Map<String, Object> attributes;


    private HttpSession(HttpSessionManager manager, String id){
        this.manager = manager;
        this.id = id;
        this.attributes = new HashMap<>();
        this.manager.setHttpSession(this);
    }

    public static HttpSession of(HttpSessionManager manager, String id) {
        return new HttpSession(manager, id);
    }

    public String getId() {
        return this.id;
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public void invalidate() {
        this.manager.invalidate(this.id);
    }

    public String getCookieValue() {
        return new HttpCookie(this.manager.getSessionKeyName(), this.getId()).getCookieNameAndValue();
    }
}
