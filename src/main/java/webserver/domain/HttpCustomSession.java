package webserver.domain;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HttpCustomSession extends CustomSession {

    private String sessionId;
    private Map<String, Object> attributes;

    public HttpCustomSession() {
        this.sessionId = UUID.randomUUID().toString();
        this.attributes = Maps.newConcurrentMap();
    }

    @Override
    public String getId() {
        return this.sessionId;
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    @Override
    public void invalidate() {
        this.attributes.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpCustomSession that = (HttpCustomSession) o;
        return Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}
