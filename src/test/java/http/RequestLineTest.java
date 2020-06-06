package http;

import org.junit.jupiter.api.Test;
import utils.HttpStringBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @Test
    public void parseTest() throws IOException {
        RequestLine requestLine = RequestLine.from("GET /users?userId=1 HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getProtocolVersion()).isEqualTo("1.1");
    }
}
