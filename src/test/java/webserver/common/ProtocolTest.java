package webserver.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.common.exception.IllegalProtocolException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProtocolTest {

    @DisplayName("HTTP/1.1 문자열을 Protocol enum 으로 변환할 수 있다.")
    @Test
    void httpProtocol() {
        assertThat(Protocol.from("HTTP/1.1"))
                .isEqualTo(Protocol.HTTP_1_1);
    }

    @DisplayName("부적절한 Protocol 일 경우, IllegalProtocolException 이 발생한다.")
    @Test
    void illegalProtocol() {
        assertThatThrownBy(
                () -> Protocol.from("ILLEGAL_PROTOCOL")
        ).isInstanceOf(IllegalProtocolException.class);
    }
}
