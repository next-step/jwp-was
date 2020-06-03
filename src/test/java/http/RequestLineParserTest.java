package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineParserTest {

    @Test
    void parse() {
        RequestLine requestLine = RequestLineParser.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getMapping()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.protocol.);
    }
}

