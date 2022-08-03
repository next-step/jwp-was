package webserver.http.domain.session;

import java.util.UUID;

public class SessionIdGenerator {

    public String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
