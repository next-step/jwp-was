package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ResponseHeader 테스트")
class ResponseHeaderTest {

    @DisplayName("textHtml Header 생성 테스트")
    @Test
    void textHtml() {
        Map<HttpHeaders, Object> map = new HashMap<>();
        map.put(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8");

        ResponseHeader result = ResponseHeader.of(map);

        assertThat(result.getHeaders()).contains(
                Map.entry(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8")
        );
    }

}
