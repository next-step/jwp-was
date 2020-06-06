package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class ProtocolTest {

    @DisplayName("프로토콜 정보를 이름과 버전으로 파싱한다.")
    @Test
    void test_parsing_protocol_should_pass() {
        // given
        String protocolInfo = "HTTP/1.1";
        // when
        Protocol protocol = Protocol.from(protocolInfo);
        // then
        assertThat(protocol.equals(Protocol.of("HTTP", "1.1"))).isTrue();
    }
}
