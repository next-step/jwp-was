package http.requests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("리퀘스트 컨텍스트 테스트")
class HttpRequestTest {

    @DisplayName("리퀘스트 컨텍스트가 쿼리스트링을 제외한 순수한 uri만 뽑아내는지 테스트")
    @Test
    void test_get_path() throws Exception {
        final String testRequest =
                "GET /index.html?amu=mal&dae=janchi HTTP/1.1" +
                        "Host: localhost:8080\r\n" +
                        "Content-Type: application/x-www-form-urlencoded\r\n" +
                        "\r\n";
        try (final InputStream input = new ByteArrayInputStream(testRequest.getBytes())) {
            final HttpRequest httpRequest = new HttpRequest(input);
            assertThat(httpRequest.getPath()).isEqualTo("/index.html");
        }
    }
}