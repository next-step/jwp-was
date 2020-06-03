package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProtocolTest {
    @Test
    void invalid() {
        assertThatThrownBy(() -> new Protocol("HTTP"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Protocol("ABC/1.1"))
                .isInstanceOf(RequestLineParser.class);
    }

    @Test
    void create() {
        Protocol protocol = new Protocol("HTTP/1.1");
        assertThat(protocol).isEqualTo(new Protocol("HTTP", "1.1"));
    }
}
