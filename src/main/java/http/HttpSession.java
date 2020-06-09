package http;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * @author KingCjy
 */
public class HttpSession extends LinkedHashMap<String, Object> {

    private String id;

    public HttpSession() {
        this.id = UUID.randomUUID().toString();
        HttpSessionManager.addSession(this);
    }

    public String getId() {
        return id;
    }

    public void addAttribute(String key, Object value) {
        put(key, value);
    }

    public Object getAttribute(String key) {
        return get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HttpSession that = (HttpSession) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
