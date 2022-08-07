package webserver.http;

import java.util.UUID;

public class HttpSession {
    private final String id;

    public HttpSession() {
        this.id = generateId();
    }

    public String getId() {
        return id;
    }

    private String generateId() {
        final UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
