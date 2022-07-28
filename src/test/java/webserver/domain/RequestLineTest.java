package webserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestLineTest {
    private static final String REQUEST_LINE = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";

    @DisplayName("유효한 요청 문자열로 객체를 생성할 수 있다.")
    @Test
    void fromWithValidLine() {
        RequestLine requestLine = RequestLine.from(REQUEST_LINE);

        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestLine.getPath()).isEqualTo(new Path("/user/create")),
                () -> assertThat(requestLine.getPathStr()).isEqualTo("/user/create"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo(Protocol.newInstance("HTTP/1.1"))
        );
    }

    @DisplayName("유효하지 않은 문자열이 인수인 경우 예외를 던진다.")
    @ParameterizedTest
    @NullAndEmptySource
    void fromWithInvalidLine(String invalidLine) {
        assertThatThrownBy(() -> RequestLine.from(invalidLine))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(RequestLine.INVALID_REQUEST_LINE_MSG + invalidLine);
    }
}
