package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpRequest 단위 테스트")
class HttpRequestTest {
    @DisplayName("GET HTTP 요청을 생성한다.")
    @Test
    void createGet() {
        // given
        final String request = "GET /user/create HTTP/1.1\r\n"
                + "Host: localhost:8080\n\n";
        final InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));

        // when
        final HttpRequest httpRequest = new HttpRequest(in);

        // then
        assertAll(
                () -> assertThat(httpRequest.getRequestLine()).isEqualTo(
                        new RequestLine(
                                HttpMethod.GET,
                                new HttpPath("/user/create"),
                                new HttpProtocol("HTTP/1.1")
                        )
                ),
                () -> assertThat(httpRequest.getPayloads()).isEqualTo(new HashMap<>())
        );
    }

    @DisplayName("POST HTTP 요청을 생성한다.")
    @Test
    void createPost() {
        // given
        final String request = "POST /user/create HTTP/1.1\r\n"
                + "Host: localhost:8080\r\n"
                + "Connection: keep-alive\r\n"
                + "Content-Length: 29\r\n"
                + "Content-Type: application/x-www-form-urlencoded\r\n"
                + "Accept: */*\r\n"
                + "\r\n"
                + "userId=user&password=password";
        final InputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));

        // when
        final HttpRequest httpRequest = new HttpRequest(in);

        // then
        assertAll(
                () -> assertThat(httpRequest.getRequestLine()).isEqualTo(
                        new RequestLine(
                                HttpMethod.POST,
                                new HttpPath("/user/create"),
                                new HttpProtocol("HTTP/1.1")
                        )
                ),
                () -> assertThat(httpRequest.getContentLength()).isEqualTo(29),
                () -> assertThat(httpRequest.getPayloads()).isEqualTo(Map.of("userId", "user", "password", "password"))
        );
    }

    @DisplayName("잘못된 요청으로 HTTP 요청을 생성하면 예외를 발생한다.")
    @Test
    void createException() {
        // given
        final InputStream in = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));

        // when & then
        assertThatThrownBy(() -> new HttpRequest(in))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(HttpRequest.VALIDATION_MESSAGE);
    }
}
