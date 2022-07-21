package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class HttpProtocolTest {

    @DisplayName("HTTP Protocol 문자열을 파싱한다.")
    @Test
    void HttpProtocolTest() {
        HttpProtocol httpProtocol = new HttpProtocol("HTTP/1.1");

        assertAll(
                () -> assertThat(httpProtocol.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(httpProtocol.getVersion()).isEqualTo("1.1"));

    }

    @DisplayName("HTTP Protocol 문자열 형식이 올바르지 않으면 예외가 발생한다.")
    @Test
    void HttpProtocolExceptionTest() {
        assertThatThrownBy(() -> new HttpProtocol("HTTP")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("HttpProtocol 형식이 올바르지 않습니다.");
    }
}
