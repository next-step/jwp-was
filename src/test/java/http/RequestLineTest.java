package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("리퀘스트 라인 테스트")
public class RequestLineTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("request line 포멧에 맞지 않을경우 예외 발생")
    void initFail(final String requestLine) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> RequestLine.init(requestLine));
    }

    private static Stream<String> initFail() {
        return Stream.of(
                "GETS test HTTP/1.1",
                "GET test HTTP",
                "GET test 1.1",
                "GET test 1.1 other",
                "GET test?wweoifjoi HTTP/1.1"
        );
    }

    @Test
    @DisplayName("Get 파싱")
    void parse() {
        RequestLine requestLine = RequestLine.init("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    @DisplayName("Post 파싱")
    void parsePost() {
        RequestLine requestLine = RequestLine.init("POST /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    @DisplayName("QueryString 파싱")
    void parseQueryString() {
        RequestLine requestLine = RequestLine.init("GET /users?name1=value1&name2=value2 HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getParameter("name1")).isEqualTo("value1");
        assertThat(requestLine.getParameter("name2")).isEqualTo("value2");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }
}
