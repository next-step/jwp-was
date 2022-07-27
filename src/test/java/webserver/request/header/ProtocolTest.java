package webserver.request.header;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.request.header.exception.InvalidProtocolException;
import webserver.request.header.Protocol;

class ProtocolTest {

    @DisplayName("지원되지 않는 protocol의 경우 예외가 발생해야 한다.")
    @Test
    void createFailedByProtocolName() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Protocol.from("TEST"))
                .isInstanceOf(InvalidProtocolException.class)
                .hasMessage("유효하지 않은 프로토콜 입니다.");
    }
}
