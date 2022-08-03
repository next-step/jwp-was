package webserver.http.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpHeadersTest {

    @Test
    void 헤더_파싱() {
        String key1 = "Host";
        String key2 = "Connection";
        String key3 = "Accept";
        String value1 = "localhost:8080";
        String value2 = "keep-alive";
        String value3 = "*/*";

        HttpHeaders headers = HttpHeaders.from(
                key1 + ": " + value1 + "\n" +
                        key2 + ": " + value2 + "\n" +
                        key3 + ": " + value3
        );

        assertAll(
                () -> assertThat(headers.get(key1)).isEqualTo(value1),
                () -> assertThat(headers.get(key2)).isEqualTo(value2),
                () -> assertThat(headers.get(key3)).isEqualTo(value3)
        );
    }
}