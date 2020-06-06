package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("raw http request 를 HttpRequest 객체로 파싱해주는 클래스")
class HttpRequestParserTest {

    @Test
    void parse() {
        HttpRequest httpRequest = HttpRequestParser.parse(Statics.RAW_REQUEST_STR);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/foo/bar");
        assertThat(httpRequest.getParameter("zoo")).isEqualTo("xoo");
        assertThat(httpRequest.getProtocol()).isEqualTo("HTTP");
        assertThat(httpRequest.getVersion()).isEqualTo("1.1");
    }
}