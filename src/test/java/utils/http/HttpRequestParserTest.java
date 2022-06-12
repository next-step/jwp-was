package utils.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestParserTest {

    @Test
    void parse() {
        // given
        String requestLine = "GET /users HTTP/1.1";

        // when
        HttpRequest httpRequest = HttpRequestParser.parse(requestLine);

        // then
        HttpRequest expected = new HttpRequest("GET", "/users", "HTTP", "1.1");
        assertThat(httpRequest).isEqualTo(expected);
    }
}
