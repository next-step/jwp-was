package webserver.session;

import webserver.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpUUIDSession implements HttpSession {

    private String uuid;
    private Map<String, Object> attributes = new HashMap<>();

    private HttpUUIDSession(String uuid) {
        this.uuid = uuid;
    }

    public static HttpSession of() {
        String uuid = UUID.randomUUID().toString();
        return of(uuid);
    }

    public static HttpSession of(String uuid) {
        return new HttpUUIDSession(uuid);
    }

    @Override
    public String getId() {
        return uuid;
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        attributes.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpUUIDSession that = (HttpUUIDSession) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "HttpUUIDSession{" +
                "uuid='" + uuid + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
