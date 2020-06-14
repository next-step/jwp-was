package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseHeadersTest {

    @DisplayName("응답헤더 추가")
    @Test
    void addHeader() {

        // given
        String name = "Content-Type";
        String[] values = {"text/html", "charset=utf-8"};
        ResponseHeaders responseHeaders = ResponseHeaders.of();

        // when
        responseHeaders.addHeader(name, values);

        // then
        assertThat(responseHeaders.getResponseHeaders().get(name))
                .isEqualTo(ResponseHeader.of(name, values));
    }
}