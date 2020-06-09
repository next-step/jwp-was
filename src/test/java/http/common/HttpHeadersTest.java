package http.common;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpHeadersTest {

    @ParameterizedTest
    @NullSource
    void testForNull(Map<String, String> httpHeaderMap) {
        assertThrows(IllegalArgumentException.class, () -> new HttpHeaders(httpHeaderMap));
    }

    @Test
    void testForEmpty() {
        Map<String, String> httpHeaderMap = Maps.newHashMap();

        HttpHeaders httpHeaders = new HttpHeaders(httpHeaderMap);

        assertThat(httpHeaders).isNotNull();
        assertThat(httpHeaders.isNotEmpty()).isFalse();
    }

    @Test
    void testHttpHeaders() {
        Map<String, String> httpHeaderMap = Maps.newHashMap();

        httpHeaderMap.put("Origin", "http://localhost:8080");
        httpHeaderMap.put("Connection", "keep-alive");
        httpHeaderMap.put("Content-Length", "89");
        httpHeaderMap.put("Content-Type", "application/x-www-form-urlencoded");

        HttpHeaders httpHeaders = new HttpHeaders(httpHeaderMap);

        assertThat(httpHeaders).isNotNull();
        assertThat(httpHeaders.isNotEmpty()).isTrue();
        assertThat(httpHeaders.getHeaderValue("Origin")).isEqualTo("http://localhost:8080");
        assertThat(httpHeaders.getHeaderValue("Connection")).isEqualTo("keep-alive");
        assertThat(httpHeaders.getHeaderValue("Content-Length")).isEqualTo("89");
        assertThat(httpHeaders.getHeaderValue("Content-Type")).isEqualTo("application/x-www-form-urlencoded");
    }
}