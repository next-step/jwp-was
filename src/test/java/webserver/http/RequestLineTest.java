package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestLineTest {

    @DisplayName("문자열을 받아 RequestLine을 생성한다")
    @Test
    void create() {
        assertThat(new RequestLine("GET /users HTTP/1.1")).isNotNull();
    }

    @DisplayName("잘못된 형식의 문자열의 경우 Exception이 발생한다")
    @ParameterizedTest(name = "잘못된 문자열 \"{0}\"")
    @CsvSource({"HELLO /users HTTP/1.1", "GET /users", })
    void invalidRequestLine(String requestLine) {
        assertThatThrownBy(() -> new RequestLine(requestLine))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
