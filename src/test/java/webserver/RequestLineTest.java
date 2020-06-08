package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @DisplayName("[GET] HTTP 요청의 requestLine 파싱")
    @Test
    void parse_get() {
        // given
        String requestLineText = "GET /docs/index.html HTTP/1.1";

        // when
        RequestLine requestLine = new RequestLine(requestLineText);

        // then
        assertThat(requestLine)
                .isEqualTo(new RequestLine("GET", "/docs/index.html", new Protocol("HTTP", "1.1")));
    }
}
