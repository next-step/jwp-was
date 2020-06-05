package http.requests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("리퀘스트 컨텍스트 테스트")
class HttpRequestTest {

    @DisplayName("리퀘스트 컨텍스트가 쿼리스트링을 제외한 순수한 uri만 뽑아내는지 테스트")
    @Test
    void test_get_path() {
        final String rawRequestLine = "GET /index.html?amu=mal&dae=janchi HTTP/1.1";
        final List<String> rawRequestHeaders = new ArrayList<>();
        rawRequestHeaders.add("Host: localhost:8080");
        rawRequestHeaders.add("Connection: keep-alive");
        rawRequestHeaders.add("Cache-Control: no-cache");

        final HttpRequest httpRequest = new HttpRequest(rawRequestLine, rawRequestHeaders, "");
        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }
}