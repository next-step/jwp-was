package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {

    @DisplayName("GET RequestLine 문자열을 파싱한다.")
    @Test
    void requestLineGetParsingTest() throws Exception {
        // given & when
        RequestLine requestLine = new RequestLine("GET /users?a=b HTTP/1.1");

        // then
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1"),
                () -> assertThat(requestLine.getParameter("a")).isEqualTo("b"),
                () -> assertThat(requestLine.getParameters().getParameters()).hasSize(1)
        );
    }

    @DisplayName("POST RequestLine 문자열을 파싱한다.")
    @Test
    void requestLinePostParsingTest() throws Exception {
        // given & when
        RequestLine requestLine = new RequestLine("POST /users HTTP/1.1");

        // then
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("RequestLine 문자열 형식이 맞지 않으면 예외가 발생한다.")
    @Test
    void requestLineExceptionTest() throws Exception {
        assertThatThrownBy(() -> new RequestLine("GET HTTP/1.1")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("RequestLine 형식이 아닙니다.");

    }

}
