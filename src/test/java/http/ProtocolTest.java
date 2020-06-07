package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProtocolTest {

    @Test
    void isValid() {
        assertThatThrownBy(() -> {
            Protocol.of("HTTP");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void create() {
        Protocol protocol = Protocol.of("HTTP/1.1");
        assertThat(protocol).isEqualTo(Protocol.of("HTTP", "1.1"));
    }
}
