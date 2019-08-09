package webserver.http.session;

import java.util.Optional;

public interface Session {

    String getId();
    void setAttribute(final String name, final Object value);
    <E> Optional<E> getAttribute(final String name);
    void removeAttribute(final String name);
    void invalidate();
}
