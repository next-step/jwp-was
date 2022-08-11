package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {
    @DisplayName("HttpHeaders add 테스트")
    @Test
    void add_test() {
        String header = "Content-Length: 59";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(header);
        assertThat(httpHeaders.get("Content-Length")).isEqualTo(59);
    }
}