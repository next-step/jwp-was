package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtocolTest {
    @Test
    void protocol() {
        String protocol = "HTTP/1.1";
        assertThat(new Protocol(protocol)).isEqualTo(new Protocol("HTTP", "1.1"));
        ;
    }
}
