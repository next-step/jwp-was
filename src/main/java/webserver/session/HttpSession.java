package webserver.session;

import java.util.UUID;

public class HttpSession {

    private String id;
    private Attributes attributes;

    public static HttpSession create() {
        HttpSession httpSession = new HttpSession();
        UUID uuid = UUID.randomUUID();
        httpSession.id = uuid.toString();
        httpSession.initAttribute();
        return httpSession;
    }

    private HttpSession() {
    }

    private void initAttribute() {
        this.attributes = new Attributes();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public void invalidate() {
        initAttribute();
    }
}
