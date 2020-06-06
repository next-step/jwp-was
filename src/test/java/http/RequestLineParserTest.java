package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("리퀘스트 라인 파서 테스트")
public class RequestLineParserTest {

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
