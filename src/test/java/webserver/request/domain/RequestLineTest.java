package webserver.request.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.exception.StringEmptyException;
import webserver.request.domain.request.RequestLine;

import static org.junit.jupiter.api.Assertions.assertAll;

public class RequestLineTest {

    @Test
    @DisplayName("requestLine이 빈 값이면 예외를 던진다.")
    public void validTest() {
        Assertions.assertThrows(StringEmptyException.class, () -> {
            RequestLine.parse("");
        });
    }

    @Test
    @DisplayName("GET 요청을 받았을때 정상적으로 파싱되었는지 테스트")
    void requestLineGetTest() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.parseMethod()).isEqualTo("GET"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.parsePath()).isEqualTo("/users"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.parseProtocol()).isEqualTo("HTTP"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.parseProtocolVersion()).isEqualTo("1.1")
        );
    }

    @Test
    @DisplayName("POST 요청을 받았을때 정상적으로 파싱되었는지 테스트")
    void requestLinePOSTTest() {
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");

        assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.parseMethod()).isEqualTo("POST"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.parsePath()).isEqualTo("/users"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.parseProtocol()).isEqualTo("HTTP"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.parseProtocolVersion()).isEqualTo("1.1")
        );
    }

}
