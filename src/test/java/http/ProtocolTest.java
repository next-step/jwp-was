package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProtocolTest {

    @Test
    void invalid() {
        assertThatThrownBy(() -> Protocol.fromString("HTTP"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void create() {
        Protocol protocol = Protocol.fromString("HTTP/1.1");

        assertThat(protocol).isEqualTo(Protocol.of("HTTP", "1.1"));
    }
}
