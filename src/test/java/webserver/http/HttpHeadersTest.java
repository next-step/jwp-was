package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpHeadersTest {

    @DisplayName("HTTP 헤더 목록을 담는 객체를 생성한다.")
    @Test
    void create() {
        HttpHeaders httpHeaders = new HttpHeaders(headers());
        assertAll(() -> {
            assertThat(httpHeaders.getAccept()).isEqualTo("application/json");
            assertThat(httpHeaders.getCookies().getCookie("logined")).isEqualTo("true");
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
