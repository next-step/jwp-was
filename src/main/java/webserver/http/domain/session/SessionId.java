package webserver.http.domain.session;

import java.util.Objects;
import java.util.UUID;

public class SessionId {

    private final String id;
    public SessionId() {
        this.id = UUID.randomUUID().toString();
    }

    private SessionId(String sessionId) {
        this.id = sessionId;
    }

    public String id() {
        return id;
    }

    public static SessionId sessionId(String sessionId) {
        return new SessionId(sessionId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionId sessionId = (SessionId) o;
        return Objects.equals(id, sessionId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
