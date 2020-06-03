package http;

import http.exceptions.UnsupportedHttpMethodException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestLineParserTest {

    @DisplayName("정상적인 GET 요청을 테스트한다.")
    @Test
    void parse_get_request() {
        final RequestLine requestLine = RequestLineParser.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("정상적인 POST 요청을 테스트한다.")
    @Test
    void parse_post_request() {
        final RequestLine requestLine = RequestLineParser.parse("POST /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("지원하지 않는 method가 request line에 들어오면 UnsupportedHttpMethodException이 발생한다.")
    @Test
    void given_non_supported_method_invokes_parse_then_throws_UnsupportedHttpMethodException() {
        assertThatThrownBy(() -> {
            RequestLineParser.parse("TENGO_MIEDO /users HTTP/1.1");
        }).isInstanceOf(UnsupportedHttpMethodException.class);
    }

    // TODO: 이건 QueryString의 책임인 듯 하므로 나중에 QueryString 테스트는 따로 빼는게 좋을 것 같다. (정신건강에)
    @DisplayName("정상적인 QueryString 요청을 테스트한다.")
    @Test
    void parse_query_string() {
        final RequestLine requestLine = RequestLineParser.parse("GET /users?userId=hyeyoom&password=1234abcd&name=Chiho HTTP/1.1");
        assertThat(requestLine.getQueryString().getParameter("userId")).isEqualTo("hyeyoom");
        assertThat(requestLine.getQueryString().getParameter("password")).isEqualTo("1234abcd");
        assertThat(requestLine.getQueryString().getParameter("name")).isEqualTo("Chiho");
    }
}
