package webserver.http.domain.session;

import java.util.UUID;

public class SessionId {

    private final String id;

    public SessionId() {
        this.id = UUID.randomUUID().toString();
    }

    public String id() {
        return id;
    }
}
