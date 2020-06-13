package http.common;

import java.util.UUID;

public class HttpSession {

    private String id;

    public HttpSession() {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    public String getId() {
        return id;
    }
}
