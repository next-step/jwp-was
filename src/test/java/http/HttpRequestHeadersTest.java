package http;

import http.request.HttpRequestHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestHeadersTest {
    @Test
    @DisplayName("Header에 값 추가")
    void addTest() {
        final HttpRequestHeaders headers = new HttpRequestHeaders();
        headers.addHeader("Authorization: Bearer");
        assertThat(headers.getHeader("Authorization")).isEqualTo("Bearer");
    }

    @Test
    @DisplayName("Content length 가지고 오기")
    void getContentLengthTest() {
        final HttpRequestHeaders httpRequestHeaders = new HttpRequestHeaders();
        httpRequestHeaders.addHeader("Content-length: 4");
        assertThat(httpRequestHeaders.getContentLength()).isEqualTo(4);
    }
}
