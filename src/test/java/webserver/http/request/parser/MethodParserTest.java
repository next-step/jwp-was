package webserver.http.request.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MethodParserTest {
    private final MethodParser methodParser = new MethodParser();

    @DisplayName("서버에서 지원하지 않는 HTTP 메소드 문자열을 입력받는 경우 예외 발생")
    @Test
    void parse_fail() {
        String invalidMessage = "INVALID_METHOD";
        assertThatThrownBy(() -> methodParser.parse(invalidMessage))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("현재 서버에서 처리할 수 없는 메소드입니다. {: " + invalidMessage + "}");
    }

    @DisplayName("HTTP 메소드 문자열 값을 입력받아 Method Enum 값을 반환한다.")
    @Test
    void parse() {
        String message = "GET";
        Method actual = methodParser.parse(message);
        assertThat(actual).isEqualTo(Method.GET);
    }
}