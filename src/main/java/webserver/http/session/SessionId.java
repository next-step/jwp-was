package webserver.http.session;

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
}
