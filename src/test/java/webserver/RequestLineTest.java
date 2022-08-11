package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class RequestLineTest {
    private static final Protocol HTTP_ONE_ONE_VERSION = Protocol.of("HTTP", "1.1");

    @Test
    void get_index() {
        RequestLine requestLine = RequestLine.from("GET /index.html HTTP/1.1");
        assertThat(requestLine).isNotNull();
        assertThat(requestLine.path()).isEqualTo("/index.html");

    }

    @DisplayName("유효한 GET 요청으로 RequestLine 파싱하는 테스트")
    @Test
    void from_get() {
        String httpRequest = "GET /users HTTP/1.1";
        RequestLine requestLine = RequestLine.from(httpRequest);
        assertThat(requestLine).isNotNull();
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getProtocol()).isEqualTo(HTTP_ONE_ONE_VERSION);
    }

    @DisplayName("유효한 POST 요청으로 RequestLine 파싱하는 테스트")
    @Test
    void from_post() {
        String httpRequest = "POST /users HTTP/1.1";
        RequestLine requestLine = RequestLine.from(httpRequest);
        System.out.println(requestLine);
        assertThat(requestLine).isNotNull();
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getProtocol()).isEqualTo(HTTP_ONE_ONE_VERSION);
    }

    @DisplayName("유효한 GET 요청(Query String 포함)으로 RequestLine 파싱하는 테스트")
    @Test
    void from_get_query_string() {
        String httpRequest = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        RequestLine requestLine = RequestLine.from(httpRequest);
        System.out.println(requestLine);
        assertThat(requestLine).isNotNull();
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getProtocol()).isEqualTo(HTTP_ONE_ONE_VERSION);
    }

    @DisplayName("잘못된 요청으로 RequestLine 파싱하는 경우 예외 발생 테스트")
    @Test
    void exception() {
        String wrongHttpRequest = "GET/usersHTTP/1.1";
        assertThatIllegalArgumentException().isThrownBy(() -> RequestLine.from(wrongHttpRequest));
    }
}
