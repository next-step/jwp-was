package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpMethodTest {

    @DisplayName("valueOf 메소드 테스트")
    @ParameterizedTest(name = "\"{0}\" 문자열을 받으면 HttpMethod {1}을 반환한다.")
    @CsvSource({"GET, GET", "POST, POST"})
    void valueOf(String source, HttpMethod expected) {
        assertThat(HttpMethod.valueOf(source)).isEqualTo(expected);
    }

    @DisplayName("잘못된 문자열을 전달하면 IllegalArgumentException이 발생한다.")
    @Test
    void valueOfThrowException() {
        assertThatThrownBy(() -> HttpMethod.valueOf("SEND"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
