package webserver.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    public HttpSession() {
        this.id = generateId();
    }

    public String getId() {
        return id;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public <T> T getAttribute(String key, Class<T> returnType) {
        return OBJECT_MAPPER.convertValue(attributes.get(key), returnType);
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

    private String generateId() {
        final UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
