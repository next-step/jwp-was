package webserver.http;

import java.util.UUID;

public class HttpSession {
    private final UUID sessionId;

    public HttpSession() {
        sessionId = UUID.randomUUID();
    }

    public UUID getSessionId() {
        return sessionId;
    }
}
