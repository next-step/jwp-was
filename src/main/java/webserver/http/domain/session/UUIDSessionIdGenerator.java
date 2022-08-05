package webserver.http.domain.session;

import java.util.UUID;

public class UUIDSessionIdGenerator implements SessionIdGenerator {

    public String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
