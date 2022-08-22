package webserver.http.session;

import java.util.Objects;
import java.util.UUID;

public class SessionId {

    private String id;

    public SessionId(String id) {
        this.id = id;
    }

    public static SessionId newInstance() {
        String uuid = UUID.randomUUID().toString();
        return new SessionId(uuid);
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
}
