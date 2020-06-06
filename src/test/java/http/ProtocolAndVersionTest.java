package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProtocolAndVersionTest {

    @Test
    void name() {
        final String input = "http/1.1";

        final ProtocolAndVersion result = new ProtocolAndVersion(input);

        assertThat(result).isEqualTo(new ProtocolAndVersion("http", "1.1"));
    }
}