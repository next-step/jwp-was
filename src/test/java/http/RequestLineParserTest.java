package http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class RequestLineParserTest {
    @Test
    void parse() {
        RequestLine requestLine = RequestLineParser.parse("GET /users HTTP/1.1");
        Assertions.assertThat(requestLine.getMethod()).isEqualTo("GET");
        Assertions.assertThat(requestLine.getPath()).isEqualTo("/users");
        Assertions.assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        Assertions.assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }
}
