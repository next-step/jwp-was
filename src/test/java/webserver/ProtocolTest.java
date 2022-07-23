package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exception.IllegalProtocolException;

class ProtocolTest {

    @DisplayName("클라이언트가 보낸 요청의 프로토콜을 파싱한다.")
    @Test
    void parse() {
        Protocol protocol = new Protocol("HTTP/1.1");
        assertThat(protocol).isEqualTo(new Protocol("HTTP", "1.1"));
    }

    @DisplayName("유효하지 않은 프로토콜일 경우, IllegalProtocolException 예외가 발생한다.")
    @Test
    void invalid() {
        assertThatThrownBy(() -> new Protocol("HTTP1.1"))
            .isInstanceOf(IllegalProtocolException.class);
    }
}
