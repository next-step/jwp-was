package webserver.http.session;

import java.util.Objects;
import java.util.UUID;

public class SessionId {

    private final String id;

    public SessionId(String id) {
        this.id = id;
    }

    public static SessionId generate() {
        UUID key = UUID.randomUUID();
        return new SessionId(key.toString());
    }

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return "SessionId{" +
                "id='" + id + '\'' +
                '}';
    }
}
