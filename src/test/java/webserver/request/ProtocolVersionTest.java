package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("프로토콜 버전")
class ProtocolVersionTest {

    @Test
    @DisplayName("파싱테스트")
    public void parsingTest() {
        ProtocolVersion protocolVersion = ProtocolVersion.findProtocolAndVersion("HTTP/1.1");

        assertThat(protocolVersion.getProtocol()).isEqualTo(new Protocol("HTTP"));
        assertThat(protocolVersion.getVersion()).isEqualTo(new Version("1.1"));
    }
}