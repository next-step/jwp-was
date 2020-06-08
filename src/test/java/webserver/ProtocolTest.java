package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtocolTest {

    @DisplayName("프로토콜 파싱")
    @Test
    void protocol() {
        // given
        String protocolNameAndVersion = "HTTP/1.1";

        // when
        Protocol protocol = new Protocol(protocolNameAndVersion);

        // then
        assertThat(protocol).isEqualTo(new Protocol("HTTP", "1.1"));
    }
}
