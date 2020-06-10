package http;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created By kjs4395 on 2020-06-10
 */
public class HttpSession {
    private String sessionId;

    private Map<String, Object> attributeMap = Maps.newHashMap();

    public HttpSession() {
        this.sessionId = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.sessionId;
    }

    public void setAttribute(String name, Object value) {
        attributeMap.put(name, value);
    }


    public Object getAttribute(String name) {

        if (!this.attributeMap.containsKey(name)) {
            throw new IllegalArgumentException("Invalid Attribute name");
        }

        return this.attributeMap.get(name);
    }


    public void removeAttribute(String name) {
        this.attributeMap.remove(name);
    }

    public void invalidate() {
        this.attributeMap = new HashMap<>();
    }
}
