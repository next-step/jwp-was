package webserver.http;

import java.util.UUID;

public class HttpSessionFactory {

    public static HttpSession create() {
        UUID uuid = UUID.randomUUID();
        return new HttpSession(uuid);
    }
}
