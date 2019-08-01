package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {
    @Test
    void parse() {
        //when
        RequestLine requestLine = RequestLine.parse("GET /user HTTP/1.1");

        //then
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/user");
    }

    @Test
    void 잘못된RequestLine_오류발생() {
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            RequestLine requestLine = RequestLine.parse("");
        });
    }
}