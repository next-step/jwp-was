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
            RequestLine.create("");
        });
    }

    @Test
    @DisplayName("GET 요청을 받았을때 정상적으로 파싱되었는지 테스트")
    void requestLineGetTest() {
        RequestLine requestLine = RequestLine.create("GET /users HTTP/1.1");

        assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getMethod()).isEqualTo("GET"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getProtocolName()).isEqualTo("HTTP"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1")
        );
    }

    @Test
    @DisplayName("POST 요청을 받았을때 정상적으로 파싱되었는지 테스트")
    void requestLinePOSTTest() {
        RequestLine requestLine = RequestLine.create("POST /users HTTP/1.1");

        assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getMethod()).isEqualTo("POST"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getProtocolName()).isEqualTo("HTTP"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1")
        );
    }

    @Test
    @DisplayName("GET요청 + Query String 이 정상적으로 파싱되었는지 테스트")
    void requestLineGetWithQueryStringTest() {
        RequestLine requestLine = RequestLine.create("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getMethod()).isEqualTo("GET"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getProtocolName()).isEqualTo("HTTP"),
                () -> org.assertj.core.api.Assertions.assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1")
        );
    }
}
