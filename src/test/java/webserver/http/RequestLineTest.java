package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.request.HttpMethod;
import webserver.http.request.exception.HttpMethodNotSupportedException;
import webserver.http.request.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author : yusik
 * @date : 2019-08-01
 */
@DisplayName("Request-Line 테스트")
public class RequestLineTest {

    @DisplayName("기본 테스트")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "GET /users/123 HTTP/1.1",
            "POST /users/123 HTTP/1.1",
    })
    void defaultTest(String requestLineString) {
        RequestLine requestLine = RequestLine.parse(requestLineString);
        assertTrue(HttpMethod.contains(requestLine.getMethod()));
        assertThat(requestLine.getRequestURI().getPath()).isEqualTo("/users/123");
    }

    @DisplayName("예외 테스트: 허용되지 않는 method")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {
            "TEST /users HTTP/1.1",
            "OPTION /users/123 HTTP/1.1",
            "XXX /users/123 HTTP/1.1",
    })
    void notSupportedMethodTest(String requestLineString) {
        assertThrows(HttpMethodNotSupportedException.class, () -> RequestLine.parse(requestLineString));
    }
}
