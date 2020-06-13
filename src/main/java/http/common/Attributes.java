package http.common;

import java.util.HashMap;
import java.util.Map;

public class Attributes {
    private Map<String, Object> attributes;

    public Attributes() {
        this.attributes = new HashMap<>();
    }

    public void put(String key, Object value) {
        attributes.put(key, value);
    }

    public Object get(String key) {
        return attributes.get(key);
    }

    public void remove(String key) {
        attributes.remove(key);
    }
}
