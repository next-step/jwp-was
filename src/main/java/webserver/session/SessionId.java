package webserver.session;

import java.util.Objects;

public class SessionId {

    private final String id;

    public SessionId(final SessionIdGenerator sessionIdGenerator) {
        this.id = sessionIdGenerator.generate();
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SessionId sessionId = (SessionId) o;
        return Objects.equals(id, sessionId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
