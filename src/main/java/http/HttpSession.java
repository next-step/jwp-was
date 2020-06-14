package http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {

    private String id;
    private Map<String, String> attributes = new HashMap<>();

    public HttpSession() {
        this.id = UUID.randomUUID().toString();
    }

    public String getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, String value) {
        this.attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    public void invalidate() {
        this.attributes.clear();
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
