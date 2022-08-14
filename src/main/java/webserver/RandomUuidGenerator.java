package webserver;

import java.util.UUID;

class RandomUuidGenerator implements IdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
