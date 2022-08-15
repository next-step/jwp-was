package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeaderTest {

    @DisplayName("정상적인 Header 테스트")
    @Test
    void testResponseHeader() {
        Map<String, Object> map = new HashMap<>();
        map.put("Content-Length", "9");
        assertThat(new ResponseHeader("Content-Length", "9"))
                .isEqualTo(new ResponseHeader(map));
    }

    @DisplayName("비어있는 Header 테스트")
    @Test
    void testResponseHeader_WithEmptyHeader() {
        assertThat(new ResponseHeader())
                .isEqualTo(new ResponseHeader(Collections.emptyMap()));
    }
}