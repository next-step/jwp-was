package webserver.http.domain.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionIdGeneratorTest {

    @Test
    void generate() {
        SessionIdGenerator sessionIdGenerator = new SessionIdGenerator();
        String generate = sessionIdGenerator.generate();
        assertThat(generate.length()).isEqualTo(32);
    }
}