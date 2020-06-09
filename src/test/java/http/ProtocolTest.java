package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProtocolTest {

    @Test
    @DisplayName("잘못된 format이 들어왔을 때 exception test")
    void invalid() {
        assertThatThrownBy(() -> new Protocol("HTTP"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void create() {
        Protocol protocol = new Protocol("HTTP/1.1");
        assertThat(protocol).isEqualTo(new Protocol("HTTP" ,"1.1"));
        assertThat(protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(protocol.getVersion()).isEqualTo("1.1");
    }
}
