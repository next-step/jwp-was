package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpHeadersTest {

    @DisplayName("HTTP 헤더 목록을 담는 객체를 생성한다.")
    @Test
    void create() {
        HttpHeaders httpHeaders = new HttpHeaders(headers());
        assertAll(() -> {
            assertThat(httpHeaders.getAccept()).isEqualTo("application/json");
            assertThat(httpHeaders.getCookie()).isEqualTo("logined=true");
            assertThat(httpHeaders.getContentLength()).isEqualTo(60);
        });
    }

    private Map<String, String> headers() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Cookie", "logined=true");
        headers.put("Content-Length", "60");
        return headers;
    }
}
