package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HeadersTest {

    @DisplayName("Header 들을 파싱할 수 있다.")
    @Test
    void parseHeaderTest() {
        // given
        List<String> headerLines = List.of(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*"
        );

        // when
        Headers headers = Headers.parseOf(headerLines);

        // then
        assertThat(headers.getValue("Host")).isEqualTo("localhost:8080");
        assertThat(headers.getValue("Connection")).isEqualTo("keep-alive");
        assertThat(headers.getValue("Accept")).isEqualTo("*/*");
    }

}