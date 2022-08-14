package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private String id;
    private Map<String, Object> attributes;

    public HttpSession(String id, Map<String, Object> attributes) {
        validate(id, attributes);
        this.id = id;
        this.attributes = attributes;
    }

    private void validate(String id, Map<String, Object> attributes) {
        validateId(id);
        validateAttributes(attributes);
    }

    private void validateAttributes(Map<String, Object> attributes) {
        if (attributes == null) {
            throw new IllegalArgumentException("전달 받은 세션 속성 값은 null 일 수 없습니다.");
        }
    }

    private void validateId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("전달 받은 세션 id 값은 null 일 수 없습니다.");
        }
    }

    public HttpSession(String id) {
        this(id, new HashMap<>());
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
        this.attributes.clear();
        SessionDatabase.deleteById(this.id);
    }
}