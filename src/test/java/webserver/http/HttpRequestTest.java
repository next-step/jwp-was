package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
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
        final BufferedReader br = new BufferedReader(new StringReader(request));

        // when
        final HttpRequest httpRequest = new HttpRequest(br);

        // then
        assertAll(
                () -> assertThat(httpRequest.makeRequestLine()).isEqualTo(
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
        final BufferedReader br = new BufferedReader(new StringReader(request));

        // when
        final HttpRequest httpRequest = new HttpRequest(br);

        // then
        assertAll(
                () -> assertThat(httpRequest.makeRequestLine()).isEqualTo(
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
        final BufferedReader br = new BufferedReader(new StringReader(""));

        // when & then
        assertThatThrownBy(() -> new HttpRequest(br))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(HttpRequest.VALIDATION_MESSAGE);
    }
}
