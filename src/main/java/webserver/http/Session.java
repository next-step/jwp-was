package webserver.http;

import java.time.Instant;

public interface Session {

    String getId();

    void setAttribute(String name, Object value);

    boolean containsAttribute(String name);

    Object getAttribute(String name);

    void removeAttribute(String name);

    void invalidate();

    void access();

    Instant createdAt();

    Instant lastAccessedAt();
}
