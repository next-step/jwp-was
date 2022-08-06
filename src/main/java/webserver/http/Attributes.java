package webserver.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Attributes {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Map<String, Object> attributes;

    public Attributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public <T> T getAttribute(String key, Class<T> returnType) {
        return OBJECT_MAPPER.convertValue(attributes.get(key), returnType);
    }

    public String getAttribute(String key) {
        return getAttribute(key, String.class);
    }
}
