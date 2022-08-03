package webserver.http.domain.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UUIDSessionIdGeneratorTest {

    @Test
    void generate() {
        UUIDSessionIdGenerator UUIDSessionIdGenerator = new UUIDSessionIdGenerator();
        String generate = UUIDSessionIdGenerator.generate();
        assertThat(generate.length()).isEqualTo(32);
    }
}