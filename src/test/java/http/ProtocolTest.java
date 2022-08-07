package http;

import http.request.protocol.Protocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProtocolTest {

    @DisplayName("프로토콜 객체 생성 성공")
    @Test
    void createProtocolSuccess() {
        Protocol protocol = Protocol.from("HTTP/1.1");

        assertAll(
                () -> assertThat(protocol.getProtocolType()).isEqualTo("HTTP"),
                () -> assertThat(protocol.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("프로토콜 타입은 소문자여서는 안된다.")
    @Test
    void protocolNotSupportLowerCase() {
        assertThatThrownBy(
                () -> Protocol.from("http/1.1")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
