package webserver.http;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HeadersTest {

    @Test
    void create() {
        Map<String, String> map = new HashMap<>();
        map.put("Host", "localhost:8080");
        map.put("Connection", "keep-alive");
        map.put("Accept", "*/*");

        Headers headers = new Headers(map);

        assertThat(headers).isNotNull();
    }
}
