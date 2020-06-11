package http;

import http.request.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ProtocolTest {

    private Protocol protocol;

    @BeforeEach
    void setUp() {
        protocol = new Protocol("HTTP/1.1");
    }

    @Test
    @DisplayName("생성")
    void create() {
        assertThat(protocol).isEqualTo(new Protocol("HTTP/1.1"));
    }

    @Test
    @DisplayName("유효성 검사")
    void validate() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Protocol("HTTP"));
    }

    @Test
    @DisplayName("프로토콜 이름 확인")
    void getProtocolName() {
        // give
        String protocolName = protocol.getProtocol();
        // when
        boolean same = protocolName.equals("HTTP");
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("프로토콜 버전 확인")
    void getProtocolVersion() {
        // give
        String protocolVersion = protocol.getVersion();
        // when
        boolean same = protocolVersion.equals("1.1");
        // then
        assertThat(same).isTrue();
    }
}
