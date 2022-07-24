package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProtocolTest {
    @Test
    void create() {
        final Protocol expected = new Protocol("HTTP", "1.1");
        final Protocol actual = new Protocol("HTTP/1.1");

        assertThat(actual).isEqualTo(expected);
    }

}
