package webserver.http.domain.session;

import java.util.Objects;
import java.util.UUID;

public class SessionId {

    public static String LOGIN_SESSION_ID = "LOGINED";
    private final String id;

    public SessionId(String sessionId) {
        this.id = sessionId;
    }
    public SessionId() {
        this(UUID.randomUUID().toString());
    }

    public String id() {
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
