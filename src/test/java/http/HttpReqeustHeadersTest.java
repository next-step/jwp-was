package http;

import http.request.HttpReqeustHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpReqeustHeadersTest {
    @Test
    @DisplayName("Header에 값 추가")
    void addTest() {
        final HttpReqeustHeaders headers = new HttpReqeustHeaders();
        headers.addHeader("Authorization: Bearer");
        assertThat(headers.getHeader("Authorization")).isEqualTo("Bearer");
    }

    @Test
    @DisplayName("Content length 가지고 오기")
    void getContentLengthTest() {
        final HttpReqeustHeaders httpReqeustHeaders = new HttpReqeustHeaders();
        httpReqeustHeaders.addHeader("Content-length: 4");
        assertThat(httpReqeustHeaders.getContentLength()).isEqualTo(4);
    }
}
