package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ProtocolTest {
    @Test
    void of() {
        Protocol protocol = Protocol.of("HTTP/1.1");
        assertThat(protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(protocol.getVersion()).isEqualTo("1.1");
    }
}
