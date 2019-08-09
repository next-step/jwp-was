package webserver.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HttpSession implements Session {

    private final Map<String, Object> attributes = new HashMap<>();

    private final String id;

    HttpSession(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(final String name,
                             final Object value) {
        attributes.put(name, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> Optional<E> getAttribute(final String name) {
        return Optional.ofNullable((E) attributes.get(name));
    }

    @Override
    public void removeAttribute(final String name) {
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        attributes.clear();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpSession)) {
            return false;
        }

        final HttpSession that = (HttpSession) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "attributes=" + attributes +
                ", id='" + id + '\'' +
                '}';
    }
}
