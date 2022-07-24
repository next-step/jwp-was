package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.Protocol;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.RequestTestConstant.*;

public class ProtocolTest {
    @Test
    @DisplayName("ProtocolVersion을 파싱하여 Protocol을 가져온다.")
    void parsingProtocolVersionAndGetProtocol() {
        // when
        Protocol protocol = new Protocol(PROTOCOL_VERSION);
        // then
        assertThat(protocol.protocol()).isEqualTo(PROTOCOL);
    }

    @Test
    @DisplayName("ProtocolVersion을 파싱하여 version을 가져온다.")
    void parsingProtocolVersionAndGetVersion() {
        // when
        Protocol protocol = new Protocol(PROTOCOL_VERSION);
        // then
        assertThat(protocol.version()).isEqualTo(VERSION);
    }

    @Test
    @DisplayName("ProtocolVersion을 파싱의 정상 동작 여부를 확인한다.")
    void parsingProtocolVersion() {
        // when
        Protocol protocol = new Protocol(PROTOCOL_VERSION);
        // then
        assertThat(protocol).isEqualTo(new Protocol(PROTOCOL, VERSION));
    }
}
