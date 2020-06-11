package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exception.InvalidProtocolFormatException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProtocolTest {

    @DisplayName("프로토콜 파싱")
    @Test
    void protocol() {
        // given
        String protocolNameAndVersion = "HTTP/1.1";

        // when
        Protocol protocol = Protocol.of(protocolNameAndVersion);

        // then
        assertThat(protocol).isEqualTo(new Protocol("HTTP", "1.1"));
    }

    @DisplayName("프로토콜 파싱 - 프로토콜 이름이 없는 경우")
    @Test
    void protocol_not_name() {

        // given
        String protocolNameAndVersion = "1.1";

        // when & then
        assertThatExceptionOfType(InvalidProtocolFormatException.class)
                .isThrownBy(() -> Protocol.of(protocolNameAndVersion));
    }

    @DisplayName("프로토콜 파싱 - 프로토콜 버전이 없는 경우")
    @Test
    void protocol_not_version() {

        // given
        String protocolNameAndVersion = "HTTP";

        // when & then
        assertThatExceptionOfType(InvalidProtocolFormatException.class)
                .isThrownBy(() -> Protocol.of(protocolNameAndVersion));
    }
}
