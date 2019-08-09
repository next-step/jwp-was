package webserver.http.session;

import java.util.UUID;

public class UUIDBaseHttpSessionGenerator implements SessionGenerator {

    @Override
    public Session generate() {
        return new HttpSession(generateUUID());
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
