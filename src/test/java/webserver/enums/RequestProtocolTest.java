package webserver.enums;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RequestProtocolTest {

    @Test
    void createHttp_1_1_Test() {
        Protocol requestProtocol = Protocol.of("HTTP/1.1");

        assertThat(requestProtocol.protocol()).isEqualTo("HTTP");
        assertThat(requestProtocol.version()).isEqualTo("1.1");
    }

    @DisplayName("프로토콜 객체를 문자열로 출력하면 응답요청 포맷에 맞는다.")
    @Test
    void createHttp_1_1_print_Test() {
        Protocol requestProtocol = Protocol.of("HTTP/1.1");

        assertThat(requestProtocol.toString()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("유효하지 않은 프로토콜 문자열은 파싱에 실패한다.")
    @ParameterizedTest
    @CsvSource(value = {"HTTP#1.1", "HTTP", "HTP/1.1", "HTTP/"})
    void createInvalidProtocolTest(String invalidProtocol) {
        assertThatThrownBy(
            () -> Protocol.of(invalidProtocol)
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
