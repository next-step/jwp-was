package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtocolTest {

    @Test
    void split() {
        Protocol protocol = new Protocol("HTTP/1.1");

        assertThat(protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(protocol.getVersion()).isEqualTo("1.1");
    }
}
