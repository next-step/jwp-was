package http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProtocolTest {

    @DisplayName("프토토콜 객체를 생성할 수 있다.")
    @Test
    void of() {
        var protocol = Protocol.of("HTTP/1.1");

        assertAll(
            () -> assertThat(protocol.getProtocolType().name()).isEqualTo("HTTP"),
            () -> assertThat(protocol.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("프토토콜 객체는 소문자를 지원하지 않는다")
    @Test
    void of2() {
        assertThatThrownBy(
            () -> Protocol.of("http/1.1")
        ).isInstanceOf(IllegalArgumentException.class);
    }
}