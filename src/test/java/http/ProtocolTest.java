package http;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProtocolTest {

    @Test
    void create() {
        Protocol protocol = Protocol.of("HTTP/1.1");
        
        assertThat(protocol)
            .isEqualTo(Protocol.of("HTTP", "1.1"));
    }
}
