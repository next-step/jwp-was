package webserver.http.session;

import java.util.Optional;

public interface Session {
    String getId();

    Optional<Object> getAttribute(String name);

    void setAttribute(String name, Object value);

    void removeAttribute(String name);

    void invalidate();
}
