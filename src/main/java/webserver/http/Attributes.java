package webserver.http;

import utils.CastingUtils;

import java.util.Map;

public class Attributes {
    private final Map<String, Object> attributes;

    public Attributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public <T> T getAttribute(String key, Class<T> returnType) {
        return CastingUtils.cast(attributes.get(key), returnType);
    }

    public String getAttribute(String key) {
        return getAttribute(key, String.class);
    }
}
