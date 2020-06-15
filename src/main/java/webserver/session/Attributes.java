package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Attributes {
    private Map<String, Object> attributes;

    public Attributes() {
        this.attributes = new HashMap<>();
    }

    public void put(String key, Object value) {
        attributes.put(key, value);
    }

    public Optional<Object> get(String key) {
        return Optional.ofNullable(attributes.get(key));
    }

    public void remove(String key) {
        attributes.remove(key);
    }
}
